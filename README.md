# EmailValidationService
[![BUILD](https://github.com/MOderkerk/emailvalidationservice/actions/workflows/maven-dev.yml/badge.svg?branch=development)](https://github.com/MOderkerk/emailvalidationservice/actions/workflows/maven-dev.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=MOderkerk_EmailValidationService&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)
[![PULLREQUEST_TO_MASTER](https://github.com/MOderkerk/emailvalidationservice/actions/workflows/pullrequest-check.yml/badge.svg)](https://github.com/MOderkerk/emailvalidationservice/actions/workflows/pullrequest-check.yml)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=MOderkerk_EmailValidationService&metric=bugs)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=MOderkerk_EmailValidationService&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=MOderkerk_EmailValidationService&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=MOderkerk_EmailValidationService&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=MOderkerk_EmailValidationService&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=MOderkerk_EmailValidationService&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=MOderkerk_EmailValidationService&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)
## Description

Small rest api for validating an email address implemented as a spring boot application with openapi 3.0 documentation

## Open APi Documentation :


| Type | Endpoint |
|------|-------|
|Website| http://localhost:8080/swagger-ui.html|
|Json   | http://localhost:8080/v3/api-docs |

## Requests:


### /api/v1/validate

Validate the given email address. You can activate a check agains a list of 3200 known oneTime Mail address provider. If
oneTimeMailAllowed is true ,you will get an error is one mail which is in the list. With the flag tryDNSCheck you can
activate a check for the mx dns entry of the domain

**Example**

> {
> "  emailAddress": "test@example.org",
>
>  "oneTimeMailAllowed": true,
>
>  "tryDNSCheck": true
> }

## Error informations


| Error Number | Description | Further information
|---|---|---
|100001 | Parameter of the request are invalid | Check your Requestbody
|20001 | Email address has no @ sign. |t
|20002 | Email address has multiple @ signs.
|20003 | Domain of email address end with a dot. Tld missing
|20004 | Domain of email address has no dot with in it.
|20005  | Recipient part is too long. | Check the email
|20006  | Recipient part starts or ends with a dot. | Check the email
|20008  | Recipient part contains forbidden or dangerous characters | check the email
|20009  | Email address has no recipient |
|20010  | Domain part is too long  > 253 chars |
|20011  | Domain part contains forbidden or dangerous characters   |
|20012  | Email address has no domain. |
|20013  | Domain starts or ends with a dash . |
|20014  | TLD starts or ends with an dot .|
|20015  | TLD part contains forbidden or dangerous characters or is an IP Address   |
|20020 | Email is on the  onetime or disposal mail list | if you accepts one time mails set oneTimeMailAllowed to true
|20022 | MX lookup failed | There is something wrong with the domain of the given email address
|999999 | Technical error | Look in the logs and open an issue here in the project. This type of error should never come :) 



