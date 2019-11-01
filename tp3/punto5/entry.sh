#!/bin/sh

# start cron y apache
( cd /usr/local/apache2/ ; ./forward_env_start_httpd ) & /usr/sbin/crond -f -l 8

