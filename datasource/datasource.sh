#!/bin/bash

FILE=enwikibooks-20200301-pages-articles.xml
ARCHIVE=$FILE.bz2

if [[ ! -f /mnt/"$FILE" ]]; then
  echo "getting $ARCHIVE"
  curl https://ftp.acc.umu.se/mirror/wikimedia.org/dumps/enwikibooks/20200301/"$ARCHIVE" --output "$ARCHIVE"
  yum install bzip2 -y
  bzip2 -d "$ARCHIVE"
  mv "$FILE" /mnt/"$FILE"
fi

echo "$FILE present"

source /usr/local/bin/docker-entrypoint
