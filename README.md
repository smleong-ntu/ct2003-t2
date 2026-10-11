# ct2004-t3
CT2004 Project Team 3 (2026)
```
JOY CHAN WAI MUN (CHEN HUIMIN)
KOH LI JIE RYAN (XU LIJIE)
KOO LING YAN
LEONG SEE MING
LIM HUIXIN VIVIAN
```

Visit [Requirements Specification][spec] for specification document
Request permissions from @smleong-ntu if not visible

# GitHub Version Control
To keep things clean, let's keep our version control process simple
1. Use command line interface for all processes
2. Push directly to main as long as your changes are locally tested

[GitHub Installation][ghconfig] for installation and initial configutation
Windows CMD: winget install --id Git.Git -e --source winget
Update config name: git config --global user.name "your-git-name"
Update config email: git config --global user.email "your-git-email"

## Cloning the repository (copy all files into your pc - do once after installation)
1. Create a folder in your pc where you want the project
2. Windows CMD to change directory to intended folder: cd /d (folderPath)
3. git clone (repo link)
4. git remote -v

ensure your output shows this. if it does not, go to step 5
```
origin  https://github.com/smleong-ntu/ct2004-t3.git (fetch)
origin  https://github.com/smleong-ntu/ct2004-t3.git (push)
```

5. [OPTIONAL] git remote set-url origin (repo link)
<!-- SETUP COMPLETE -->

## Uploading your changes
**repeatable**
<!-- Make it a habit to pull before attempting to commit and push -->
1. git pull origin (main) --rebase
2. git add (filename)
3. git commit -m "(message)"
4. git push -u origin (main)

## Branching for Bug fixes / features
**repeatable**
<!-- Ignore for now -->
1. git checkout -b (feature-branch-name)
2. git add (filename)
3. git commit -m "(message)"
4. git push -u origin (feature-branch-name)

**Branch names**
• feature/ or feat/ — For new features (e.g., feature/shopping-cart)
• bugfix/ or fix/ — For fixing an issue (e.g., bugfix/broken-checkout-button)

## Merging Branch
<!-- Ignore for now -->
**Please request for assistance from @smleong-ntu if unsure**
1. Open Pull Request using GitHub web interface for your branch
2. Wait for Review approval <!-- optional -->
3. Click Merge then perform [Delete Branch](#deleting-branch)

## Deleting Branch
<!-- POST MERGED ACCEPTED PULL REQUEST -->
<!-- Ignore for now -->
**Please request for assistance from @smleong-ntu if unsure**
1. git switch main
2. git branch <!-- confirm you are on main branch -->
3. git push origin --delete (feature-branch-name)
4. git branch -d feature/user-login
5. git fetch -p

[spec]: https://entuedu-my.sharepoint.com/:w:/r/personal/c260010_e_ntu_edu_sg/Documents/CT2004%20-%20Software%20Engineering/Specifications.docx?d=wd730d71b5d8048e4a4afa840136bc5c6&csf=1&web=1&e=73DPu9
[ghconfig]: https://youtu.be/r8jQ9hVA2qs?si=aVXLdkI_udhQNqrS&t=186