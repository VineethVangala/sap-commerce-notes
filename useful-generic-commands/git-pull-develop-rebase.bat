git stash -m "stash before develop pull"
git pull --progress -v --no-rebase "origin" develop
git branch -u origin/develop
git rebase -i
pause