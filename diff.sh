red=`tput setaf 1`
green=`tput setaf 2`
reset=`tput sgr0`

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
    echo "$green ROOT OK$reset"
else
    echo "$red ROOT not up to date$reset"
fi

dif() {
        submodules=`ls -d mogo*/`
        for submodule in $submodules
        do
                cd $submodule
		if [ x"$(git rev-parse $2)" = x"$(git rev-parse $1/$2)" ]
		then
    			echo "$green $submodule OK$reset"
		else
		    	echo "$red $submodule not up to date$reset"
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

