#!/bin/bash
POMFAR_OPTS_DEF="-Dpomfar.file=pom.properties"
POMFAR_OPTS="${POMFAR_OPTS:-${POMFAR_OPTS_DEF}}"
#
# resolve links - $0 may be a softlink
PRG="$0"
while [ -h "$PRG" ]; do
  ls=$(ls -ld "$PRG")
  link=$(expr "$ls" : '.*-> \(.*\)$')
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=$(dirname "$PRG")/"$link"
  fi
done
# Get standard environment variables
PRGDIR=$(dirname "$PRG")
# Only set POMFAR_HOME if not already set
[ -z "$POMFAR_HOME" ] && POMFAR_HOME=$(cd "$PRGDIR/.." >/dev/null; pwd)
#
POMFAR_CLASSPATH=$(echo $POMFAR_HOME/lib/*.jar | tr ' ' ':')
#
do_run () {
  java -Dprogram.name=pomfar ${POMFAR_OPTS} \
    -cp "${POMFAR_CLASSPATH}" \
    org.javastack.pomfar.PomFar $*
}
if [ "$1" = "" ]; then
  echo "$0 <file.jar|war|zip>"
else
  do_run $*
fi
