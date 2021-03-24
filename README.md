# Challenge brotherhoodmutants MELI

## Required Setup
* Java 1.8 
* IntelliJ
* Docker

## Run

Local API:
```
terminal IntelliJ

gradle docker
docker run -it brotherhoodmutants
```

API AWS:
```
/mutant

curl --location --request POST 'http://ec2-54-196-213-102.compute-1.amazonaws.com/mutant/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dna" : ["ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"]
}'

/stats
curl --location --request GET 'http://ec2-54-196-213-102.compute-1.amazonaws.com/stats'
```
