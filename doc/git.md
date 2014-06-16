##Usefull shortcuts for git

####for Play apps

Put the following line into your .profile file.
```bash
gitpushtested() { git pull && play test && git commit -am "- $1" && git push; }
gitpushfast() { git commit -am "- $1" && git push; }
gitpushpull() { git pull && git commit -am "- $1" && git push; }

alias gp=gitpushtested
alias gpf=gitpushfast
alias gpp=gitpushfast
```

And then you can call
```bash
gp some comment for the commit 
```
* git pull is performed to get all possible changes
* play test is performed and if it fails than nothing will be commit. If it succeed than step 2
* git commit -am with your comment 
* git push

Be carefull with the -am that means add all new files with git commit maybe that is not what you want then replace it with -m.

```
gp comment # testedt commit and push
gpf comment # only commit and push
gpp comment # pull commit push
```
