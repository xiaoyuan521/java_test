#!/bin/bash

LD_LIBRARY_PATH="$LD_LIBRARY_PATH:/usr/local/lib"
export LD_LIBRARY_PATH

sh_dir=`dirname "$0"`
base_dir=`cd $sh_dir;cd ..;pwd`;

paths=()
paths[0]="$base_dir/target/java_test-1.0-SNAPSHOT.jar"
paths[1]="$base_dir/target/lib/*.jar"
paths[2]="$base_dir/lib/*.jar"
paths[3]="/usr/local/lib/*.so"

for path in ${paths[@]};do
  if [ -n "$cp" ]; then
     cp="$cp:"
  fi
  cp="$cp$path"
done

cmd="java -cp $cp -D-Djava.library.path=/usr/local/bin person.zhao.cabinet.KCDBEX1"
echo "$cmd"
$cmd