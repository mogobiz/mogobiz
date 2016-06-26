cd "$(dirname "$0")"

if [ "$#" != "2" ]; then
        echo "Usage: branch.sh remote-name branch"
        echo "remote-name : usually origin"
        echo "branch : local & remote branch name"
        exit 1
fi

echo "$1 $2"
cd "$(dirname "$0")"

if [ "$#" != "2" ]; then
        echo "Usage: branch.sh remote-name branch"
        echo "remote-name : usually origin"
        echo "branch : local & remote branch name"
        exit 1
fi

echo "$1 $2"

if [ x"$(git rev-parse $2)" = x"$(git rev-parse $1/$2)" ]
then
    echo $2 and $1/$2 are the same
fi

dif() {
        submodules=`ls -d mogo*/`
        for submodule in $submodules
        do
                echo "$submodule"
                cd $submodule
		if [ x"$(git rev-parse $2)" = x"$(git rev-parse $1/$2)" ]
		then
		    echo $2 and $1/$2 are the same
		fi
                cd ..
        done
}

cd grails
dif $1 $2
cd ..
cd scala
dif $1 $2
cd ..

