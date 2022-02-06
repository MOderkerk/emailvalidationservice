# EmailValidationService
[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=MOderkerk_EmailValidationService)](https://sonarcloud.io/summary/new_code?id=MOderkerk_EmailValidationService)

## Description 
Small rest api for validating an email address inplemented as a spring boot application with openapi 3.0 documentation

## Open APi Documentation : 
| Type | Endpoint |
|------|-------|
|Website| http://localhost:8080/swagger-ui.html|
|Json   | http://localhost:8080/v3/api-docs |

## Requests:

### /api/v1/validate 
   Validate the given email address. You can activate a check agains a list of 3200 known oneTime Mail address provider. If oneTimeMailAllowed is true ,you will get an error is one mail which is in the list. With the flag tryDNSCheck you can activate a check for the mx dns entry of the domain  

**Example**

>   {
  "emailAddress": "test@example.org",
  "oneTimeMailAllowed": true,
  "tryDNSCheck": true
}

## Error informations
| Error Number | Description | Further information
|---|---|---
|100001 | Parameter of the request are invalid | Check your Requestbody
|20001 | Email address has no @ sign. |
|20002 | Email address has multiple @ signs. 
|20003 | Domain of email address end with a dot. Tld missing
|20004 | Domain of email address has no dot with in it. 
|20005  | Recipient part is too long. | Check the email 
|20008  | Recipient part contains forbidden or dangerous characters | check the email
|20010  | Domain part is too long  > 253 chars |
|20011  | Domain part contains forbidden or dangerous characters   |
|200020 | Email is on the  onetime or disposal mail list | if you accepts one time mails set oneTimeMailAllowed to true
|200022 | MX lookup failed | There is something wrong with the domain of the given email address
|999999 | Technical error | Look in the logs and open an issue here in the project. This type of error should never come :) 



