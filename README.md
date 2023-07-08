<h1>
<img src="helpers/testainers-100.png" alt="Testainers" title="Testainers">
httpbucket
</h1>

---

[![BuildWithLove](https://img.shields.io/badge/%20built%20with-%20%E2%9D%A4-ff69b4.svg "build with love")](https://github.com/testainers/httpbucket/stargazers)
[![Docker Image Version](https://img.shields.io/docker/v/testainers/httpbucket)](https://github.com/testainers/httpbucket)
[![Licence](https://img.shields.io/github/license/testainers/httpbucket?color=blue)](https://github.com/testainers/httpbucket)
[![Build](https://img.shields.io/github/actions/workflow/status/testainers/httpbucket/main.yml?branch=main)](https://github.com/testainers/httpbucket)

**httpucket** is a powerful microservice designed specifically for testing HTTP
requests with a wide range of request URLs. It serves as a valuable tool for
developers, quality assurance teams, and anyone involved in API development. By
providing a flexible and user-friendly interface, HTTPBucket simplifies the
process of testing and validating various types of HTTP requests.

## Funding

Your contribution will help drive the development of quality tools for the
Flutter and Dart developer community. Any amount will be appreciated. Thank you
for your continued support!

[![BuyMeACoffee](https://www.buymeacoffee.com/assets/img/guidelines/download-assets-sm-2.svg)](https://www.buymeacoffee.com/edufolly)

## PIX

Sua contribuição ajudará a impulsionar o desenvolvimento de ferramentas de
qualidade para a comunidade de desenvolvedores Flutter e Dart. Qualquer quantia
será apreciada. Obrigado pelo seu apoio contínuo!

[![PIX](helpers/pix.png)](https://nubank.com.br/pagar/2bt2q/RBr4Szfuwr)

## Environment variables

| Variable            | Description | Default  |
|---------------------|-------------|----------|
| HTTPBUCKET_SSL_CERT | _*TODO*_    | cert.pem |
| HTTPBUCKET_SSL_KEY  | _*TODO*_    | key.pem  |

## How to use

```shell
docker run -d --rm --name httpbucket -p 8080:8080 -p 8443:8443 testainers/httpbucket:latest
```

## How to test

```shell
curl http://localhost:8080/methods
```

```shell
curl -k https://localhost:8443/methods
```

----

## Build

```shell
docker build . -f src/main/docker/Dockerfile.native-micro --no-cache -t httpbucket
```

## Run

```shell
docker run --rm --name httpbucket -p 8080:8080 -p 8443:8443 -d httpbucket
```

----

## Self-signed certificate

```shell
openssl req \
    -newkey rsa:2048 \
    -new \
    -nodes \
    -x509 \
    -days 3650 \
    -keyout key.pem \
    -out cert.pem \
    -subj "/C=US/ST=SC/L=Hometown/O=IT/emailAddress=root@localhost/CN=localhost"
```

----

## Extra endpoints

- /openapi
- /swagger-ui
- /health
- /metrics
