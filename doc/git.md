##Usefull shortcuts for git

####for Play apps

Put the following line into your .profile file.
```bash
gitpushtested() { play test && git commit -am "- $1" && git push; }
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
* first play test is performed and if it fails than nothing will be commit. If it succeed than step 2
* second git commit -am with your comment 
* third git push

Be carefull with the -am that means add all new files with git commit maybe that is not what you want then replace it with -m.

```
gp comment # testedt commit and push
gpf comment # only commit and push
gpp comment # pull commit push
```
