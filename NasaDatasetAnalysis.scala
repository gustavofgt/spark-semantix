package com.gustavo.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/** Compute the average number of friends by age in a social network. */
object NasaDatasetAnalysis {

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder
      .appName("NasaHttp")
      .master("local[*]")
      .getOrCreate()

    val filePath = "../NASA_access_log_Jul95.gz"

    val sc = SparkContext

    val nasaDF = spark.read.text(filePath)

    nasaDF.createOrReplaceTempView("log")

    val query = spark.sql("SELECT substring_index(value,' ',1) as hostname, regexp_extract(value,'(\\\\d{2}/\\\\w{3}/\\\\d{4}:\\\\d{2}:\\\\d{2}:\\\\d{2} -\\\\d{4})',1) as timestamp, substring(value, length(substring_index(value,'\"',1))+1, length(substring_index(value,'\"',2))-length(substring_index(value,'\"',1))+1) as request, regexp_extract(value,'\\\\s(\\\\d{3})\\\\s',1) as responsecode, regexp_extract(value,'\\\\s(\\\\d+)$',1) size FROM log")
      .cache()

    //1. Número de hosts únicos.
    val uniqueHostname = query
      .select("hostname")
      .agg(countDistinct("hostname").alias("distinct_hostnames_qty"))
      .show()
    //2. O total de erros 404.
    val total404Errors = query
      .select("responsecode")
      .filter("responsecode=\"404\"")
      .agg(count("responsecode").alias("404error_total_qty"))
      .show()

    query.createOrReplaceTempView("tblog")

    //3. Os 5 URLs que mais causaram erro 404.
    val topUrlErrors = spark.sql("SELECT request, count(*) 404error_qty FROM tblog WHERE responsecode = 404 GROUP BY request ORDER BY 2 desc LIMIT 5 ")
      .show(truncate = false)

    //4. Quantidade de erros 404 por dia.
    val errorsPerDay = spark.sql("SELECT substring(timestamp,1,11) as date, count(*) as 404error_qty FROM tblog WHERE responsecode = 404 GROUP BY substring(timestamp,1,11)")
      .show(truncate = false)

    //5. O total de bytes retornados.
    val totalBytes = spark.sql("SELECT sum(decimal(size)) as total_bytes from tblog")
      .show()

    spark.stop()
  }

}
  