profile=
environment="PRODUCTION"
list="SPRING_FLYWAY_SCHEMAS SPRING_DATASOURCE_USERNAME SPRING_DATASOURCE_PASSWORD SPRING_DATASOURCE_URL RDS_HOSTNAME RDS_PORT"
for key in $list; do
  value=`aws ssm get-parameters --names /$environment/$key --with-decryption --query "Parameters[*].{Value:Value}" --output text --region ap-northeast-1 --profile=$profile`
  echo "$key=$value"
done
