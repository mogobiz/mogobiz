#!/usr/bin/env bash

cd "$(dirname "$0")"

export RELEASE_VERSION=$1

preparerelease() {
	submodules=`ls -d mogo*/`
	for submodule in $submodules
	do
		echo "$submodule"
		cd $submodule
		git add .
        git commit -all -m "prepare release ${RELEASE_VERSION}"
		git push
		cd ..
	done
}

cd grails
preparerelease
cd ..
#cd scala
#preparerelease
#cd ..

git add .
git commit -all -m "prepare release ${RELEASE_VERSION}"
git push
