# spark-semantix

Qual o objetivo do comando cache​ ​em Spark?

O mesmo código implementado em Spark é normalmente mais rápido que a implementação equivalente em
MapReduce. Por quê?

Qual é a função do SparkContext​?

Explique com suas palavras o que é Resilient​ ​Distributed​ ​Datasets​ (RDD).

GroupByKey​ ​é menos eficiente que reduceByKey​ ​em grandes dataset. Por quê?

Explique o que o código Scala abaixo faz.
val textFile = sc.textFile("hdfs://...")
val counts = textFile.flatMap(line => line.split(" "))
.map(word => (word, 1))
.reduceByKey(_ + _)
counts.saveAsTextFile("hdfs://...")
