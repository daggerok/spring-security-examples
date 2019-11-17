# spring-security, oauth2, jwt, openid...

## step-0

```bash
./mvnw -f spring-security-oauth2-jwt/step-0 spring-boot:run
curl localhost:8080
```

## step-1-basic

```bash
./mvnw -f spring-security-oauth2-jwt/step-1-basic spring-boot:run
curl -u user:password localhost:8080
```

## step-2-fb-gh

_register fb and gh apps_

* https://developers.facebook.com/apps/1357459827613700/settings/basic/
* https://github.com/settings/applications/430943

find for each of them:

* App ID: `xxxxxxxxxx...`
* App Secret: `xxxxxxxxxxxxxxxxxx...`

_add proper_

```bash
cp -Rf ./spring-security-oauth2-jwt/step-2-fb-gh/src/maim/resources/disabled.application-fb.yaml ./spring-security-oauth2-jwt/step-2-fb-gh/src/maim/resources/application-fb.yaml
cp -Rf ./spring-security-oauth2-jwt/step-2-fb-gh/src/maim/resources/disabled.application-gh.yaml ./spring-security-oauth2-jwt/step-2-fb-gh/src/maim/resources/application-gh.yaml

vi ./spring-security-oauth2-jwt/step-2-fb-gh/src/maim/resources/application-fb.yaml
vi ./spring-security-oauth2-jwt/step-2-fb-gh/src/maim/resources/application-gh.yaml

vi ./spring-security-oauth2-jwt/step-2-fb-gh/src/maim/resources/application.yaml
```

_run gh app_

```bash
./mvnw -f spring-security-oauth2-jwt/step-2-fb-gh spring-boot:run
curl -u user:password localhost:8080
```

open http://127.0.0.1:8080/

_run fb app_

```bash
./mvnw -f spring-security-oauth2-jwt/step-2-fb-gh spring-boot:run
```

open https://127.0.0.1:8443/
