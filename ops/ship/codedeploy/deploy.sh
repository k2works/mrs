aws deploy push \
--application-name MrsOrgVPCMrsProduction \
--s3-location s3://mrsorg-vpc-mrsproduction-deploy-bucket/app.zip \
--source ./ops/ship/codedeploy \
--ignore-hidden-files \
--profile k2works \
--region ap-northeast-1
