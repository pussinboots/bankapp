##Usefull shortcuts for git

####for Play apps

Put the following line into your .profile file.
```bash
gitpush() { play test && git commit -am "- $1" && git push; }

alias gp=gitpush
```

And then you can call
```bash
gp some comment for the commit 
```

and it will first perform play test than git commit -am with your comment an than push. But be carefull with the 
-am that means add all new files to the git commit maybe that is not what you want then replace it with -m.
