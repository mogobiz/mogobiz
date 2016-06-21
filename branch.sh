cd "$(dirname "$0")"

if [ "$#" != "2" ]; then
	echo "Usage: branch.sh remote-name branch"
	echo "remote-name : usually origin"
	echo "branch : local & remote branch name"
	exit 1
fi

echo "$1 $2"
git checkout -b $2
git checkout $2
git push $1 $2

createbranch() {
	submodules=`ls -d mogo*/`
	git checkout -b $2
	git checkout $2
	git push $1 $2
	for submodule in $submodules
	do
		echo "$submodule"
		cd $submodule
		git checkout -b $2
		git checkout $2
		git push $1 $2
		cd ..
	done
}

cd grails
createbranch $1 $2
cd ..
cd scala
createbranch $1 $2
cd ..
