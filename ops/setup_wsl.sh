# bin/sh

sudo apt update
sudo apt install software-properties-common -y
sudo apt-add-repository --yes --update ppa:ansible/ansible
sudo apt-get install ansible -y
sudo ansible-playbook -v -i ./build/ansible/inventory/hosts ./build/ansible/site_wsl.yml
