# spark-semantix

Qual o objetivo do comando cache​ ​em Spark?
R: Após a execução da "action", o conteúdo do RDD é armazenado em cache.

O mesmo código implementado em Spark é normalmente mais rápido que a implementação equivalente em
MapReduce. Por quê?
O Spark otimiza a utilização de memória e reduz o I/O em relação ao MR.
A própria utilização de cache é uma vantagem.
E o próprio DAG é uma espécie de "execution planner" que otimiza melhor as operações.
Por fim o próprio tempo de inicialização dos jobs em spark é menor do que em MR.

Qual é a função do SparkContext​?
É o ponto de inicialização ou entrada no spark. A partir dele fazemos a criação dos RDDs.

Explique com suas palavras o que é Resilient​ ​Distributed​ ​Datasets​ (RDD).
São datasets particionados e tolerantes a falhas.

GroupByKey​ ​é menos eficiente que reduceByKey​ ​em grandes dataset. Por quê?
O reduceByKey faz uma agregação inicial em cada partição, para depois enviar os dados para a agragação com as demais partições, reduzindo a quantidade de dados trafegados pela rede.

Explique o que o código Scala abaixo faz.
val textFile = sc.textFile("hdfs://...")
val counts = textFile.flatMap(line => line.split(" "))
.map(word => (word, 1))
.reduceByKey(_ + _)
counts.saveAsTextFile("hdfs://...")

1. Gera um RDD a partir de um diretório/arquivo no hdfs
2. Conta a quantidade de vezes que cada palavra aparece no arquivo, e salva em um arquivo no HDFS, com o "schema": (palavra, quantidade)
