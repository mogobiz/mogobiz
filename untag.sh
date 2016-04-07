cd "$(dirname "$0")"

if [ "$#" != "2" ]; then
	echo "Usage: untag.sh remote-name tag"
	echo "remote-name : usually origin"
	echo "tag : tag name"
	exit 1
fi

echo "$1 $2"
git tag -d v$2
git push $1 :refs/tags/v$2

rmtag() {
	submodules=`ls -d mogo*`
	echo $submodules
	git tag -d v$2
	git push $1 :refs/tags/v$2
	for submodule in $submodules
	do
		echo "$submodule"
		cd $submodule
		git tag -d v$2
		git push $1 :refs/tags/v$2
		cd ..
	done
}

cd grails
rmtag $1 $2
cd ..
cd scala
rmtag $1 $2
cd ..

