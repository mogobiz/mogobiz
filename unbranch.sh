cd "$(dirname "$0")"

if [ "$#" != "2" ]; then
	echo "Usage: branch.sh remove-name branch"
	echo "remote-name : usually origin"
	echo "branch : local & remote branch name"
	exit 1
fi

echo "$1 $2"
git branch -d $2
git branch -dr origin/$2

delbranch() {
	submodules=`ls -d mogo*/`
	git branch -d $2
	git branch -dr origin/$2
	for submodule in $submodules
	do
		echo "$submodule"
		cd $submodule
		git branch -d $2
		git branch -dr origin/$2
		cd ..
	done
}

cd grails
delbranch $1 $2
cd ..
cd scala
delbranch $1 $2
cd ..

