
#### Object: vacation
#### Type: Deployment

### Cluster에 vacation Deployment를 생성하려면 아래의 명령어를 실행하세요.

```
$ kubectl create -f https://minio.msaez.io/labs-msaez.io/yamlStorage/undefined/kykim97%40uengine.org/Deployment/vacation.yaml?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minio%2F20230309%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230309T054853Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=d8346c28e18815858677d4c4d1da9bdd59dd94a766b399c7724ef744ccf94336
```
- Yaml 파일에 명시된 스펙으로 vacation Deployment를 생성합니다.

```
$ kubectl apply -f https://minio.msaez.io/labs-msaez.io/yamlStorage/undefined/kykim97%40uengine.org/Deployment/vacation.yaml?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minio%2F20230309%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230309T054853Z&X-Amz-Expires=60&X-Amz-SignedHeaders=host&X-Amz-Signature=d8346c28e18815858677d4c4d1da9bdd59dd94a766b399c7724ef744ccf94336
```
- Create가 된 상태라면 vacation Deployment의 수정이 이루어지고, Create가 된 상태가 아니라면 vacation Deployment를 Create 해주는 명령어입니다.  
#

### vacation Deployment가 정상적으로 생성되었는지 확인하시려면 아래의 명령어를 실행하세요.

```
$ kubectl get Deployment

NAME            READY   UP-TO-DATE   AVAILABLE   AGE
vacation           3/3     3            3           5m43s

```
- vacation Deployment와 Pod, Replicaset이 모두 확인이 된다면 정상적으로 생성된 것입니다.
#

### 생성된 vacation Deployment의 상세 실행 정보를 확인하시려면 아래의 명령어를 입력하세요.

```
$ kubectl describe Deployment vacation
```
- vacation Deployment의 상태를 확인하여 문제가 있는지 확인합니다. 
#

### Kubernetes Cluster network 외부에서 service에 access할 때, 해당 명령어로 외부 IP traffic을 허용할 수 있습니다.

```
$ kubectl port-forward Deployment/vacation 8080:8080
```
#

### vacation Deployment를 삭제하려면 아래의 명령어를 실행하세요.

```
$ kubectl delete Deployment vacation
```
#

