#!/usr/bin/env bash

DAY="$1"

create_package(){
  cp -r "src/main/java/com/github/redawl/aocdayTEMPLATE" "src/main/java/com/github/redawl/aocday${DAY}"
  mv src/main/java/com/github/redawl/aocday"$DAY"/AOCDay{TEMPLATE,"$DAY"}.java

  cp -r "src/test/java/com/github/redawl/aocdayTEMPLATE" "src/test/java/com/github/redawl/aocday${DAY}"
  mv src/test/java/com/github/redawl/aocday"$DAY"/AOCDay{TEMPLATE,"$DAY"}Tests.java
}

create_main(){
  sed -i "s/TEMPLATE/$DAY/" src/main/java/com/github/redawl/aocday"${DAY}"/AOCDay"${DAY}".java
  sed -i "s/TEMPLATE/$DAY/" src/test/java/com/github/redawl/aocday"${DAY}"/AOCDay"${DAY}"Tests.java
}

create_package
create_main