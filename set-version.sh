if [ "$#" != "xi12" ]; then
        echo "Usage: set-version.sh VERSION"
        exit 1
fi
cd grails
mvn -e versions:set -DnewVersion=$1
cd ..
