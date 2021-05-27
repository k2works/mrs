# AmazonLinuxの最新イメージを使う
FROM amazonlinux

# 必要なをインストールする
RUN yum -y install which procps sudo

# OpenSSH サーバをインストールする
RUN yum -y install openssh-server

# sshでログインできるようにする
RUN sed -ri 's/^#PermitRootLogin yes/PermitRootLogin yes/' /etc/ssh/sshd_config

# root のパスワードを 設定
RUN echo 'root:rootpass' | chpasswd

# 使わないにしてもここに公開鍵を登録しておかないとログインできない
RUN ssh-keygen -t rsa -N "" -f /etc/ssh/ssh_host_rsa_key

# sshのポートを22 => 20022に変更します
RUN sed -i 's/#Port 22/Port 20022/' /etc/ssh/sshd_config

# ホスト側にある公開鍵をイメージ側に登録します
COPY ../../../config/ssh/id_rsa.pub /root/.ssh/authorized_keys

# sshd の使うポートを公開する
EXPOSE 20022

# sshd を起動する
CMD ["/usr/sbin/sshd", "-D"]
