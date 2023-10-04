# Springboot3-example

Application Springboot3/JAVA17.
L'application est un webservice REST simpliste pour gérer une liste d'employés identifiés par leur addresse email. 



Pour travailer en local, il est possible de lancer l'application avec docker compose.

## DOCKER COMPOSE
Le fichier .env contient les Variables nécéssaire pour lancer la base de données postgres et l'application springboot.
On peux créer les conteneur avec les commandes suivantes
```sh
docker compose up db
docker compose up app
```

## Quelques URLS du webService:
SWAGGER: http://localhost:8080/swagger-ui/index.html#/
Health: http://localhost:8080/actuator/health

## CI/CD
Le projet possède une CI/CD  capable de tester, builder et de releaser l'application. 
Une étape de déployment sur kubernetes est également présente mais reste à terminer faute d'instance kubernetes accessible.

## KUBERNETES
Pour la partie deploiement kubernetes, est est fonctionnelle en local (ex minikube). 
Les containers utilisent les images présentes dans le container registry Gitlab du groupe de projets 

Il y a des fichiers dans le dossier deployment/ pour pouvoir déployer l'application sur kubernetes. 
Pour les fichier de deploiement de la base de données postgres il sont dans le projet https://gitlab.com/kube-infra-demo/postgres-infra

Fichiers K8S pour l'application:
- app-deployment.yml contient le deploiement de l'application.
  - l'application est exposé via une servie Nodeport et un Ingress.
    - il y a les deux car NotePorte est plus stable que Ingress dans mon minikube sur windows WSL2.
- app-deployment-ci.yml est destiné à être utilisé par la CI/CD de gitlab 

Des ressources supplémentaires de type secret et configmap son nécéssaires pour déployer.

Le détails des commandes pour creer toute couches dans kubernetes:
Les commandes présentées plus loin partent du principe que les projets du groupe sont présent localement cote à cote. (ex:)
```
├── angular-example
├── platon
├── postgres-infra
└── springboot3-example
```

Les commandes sont a jouer dans cet ordres:
```sh
***************INGRESS & NAMESPACE**********************
kubectl create namespace staging
kubectl apply -f postgres-infra/ingress.yml --namespace=staging

*******************POSTGRES*****************

kubectl create secret --namespace=staging generic postgresql-pass --from-literal=postgresql-root-password=sb57g9qQU2wQ7Q --from-literal=postgresql-user-password=M3P@ssw0rd!
kubectl apply -f postgres-infra/deployment/config-map.yml --namespace=staging
kubectl apply -f postgres-infra/deployment/deployment.yml --namespace=staging

*******************SPRINGBOOT*****************

kubectl create configmap --namespace=staging springboot-config-map --from-literal=db-url=jdbc:postgresql://postgresql-service.staging.svc.cluster.local:5432/demoDb
kubectl apply -f springboot3-example/deployment/app-deployment-local.yml --namespace=staging

*******************ANGULAR*****************
kubectl apply -f angular-example/deployment/app-deployment-local.yml --namespace=staging
```