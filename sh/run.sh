#!/bin/bash

sh_dir=`dirname "$0"`
base_dir=`cd $sh_dir;cd ..;pwd`;

paths=()
paths[0]="$base_dir/target/java_test-1.0-SNAPSHOT.jar"
paths[1]="$base_dir/conf/*"

for path in ${paths[@]};do
  if [ -n "$cp" ]; then
     cp="$cp:"
  fi
  cp="$cp$path"
done

cmd="java -cp $cp person.zhao.mapred.TestSum"
echo "$cmd"
$cmd