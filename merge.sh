cd "$(dirname "$0")"

if [ "$#" != "2" ]; then
	echo "Usage: merge.sh source dest"
	echo "merge : source & dest branch name"
	exit 1
fi

export source=$1
export dest=$2

echo "$source $dest"
git checkout -b $dest
git checkout $dest
#git push $dest
git merge -m "Merge branch $source into $dest" --no-ff $source		

mergebranch() {
	submodules=`ls -d mogo*/`
    git checkout -b $dest
	git checkout $dest
#	git push $dest
    git merge -m "Merge branch $source into $dest" --no-ff $source		
	for submodule in $submodules
	do
		echo "$submodule"
		cd $submodule
        git checkout -b $dest
		git checkout $dest
#		git push $dest
        git merge -m "Merge branch master into develop" --no-ff master		
		cd ..
	done
}

cd grails
mergebranch
cd ..
cd scala
mergebranch
cd ..
