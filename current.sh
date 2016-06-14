cd "$(dirname "$0")"


current() {
	submodules=`ls -d mogo*/`
	git rev-parse --abbrev-ref HEAD
	for submodule in $submodules
	do
		cd $submodule
		branch=`git rev-parse --abbrev-ref HEAD`
		echo "$branch $submodule"
		cd ..
	done
}

cd grails
current
cd ..
cd scala
current
cd ..
