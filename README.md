# CT2004 Project Team 3 (2026)
```
ESWAN BIN ESA'RI
LEONG SEE MING
LIM HUIXIN VIVIAN
LOH CHEE JUN
```

Visit [Report][spec] for object diagrams for reference
Request permissions from [smleong-ntu][profile] if not visible

# GitHub Version Control
> To keep things clean, let's keep our version control process simple
1. Use command line interface for all processes
2. Push directly to main as long as your changes are locally tested

> Start Here: [GitHub Installation][ghconfig] installation and initial configutation
- Windows CMD: `winget install --id Git.Git -e --source winget`
- Update config name: `git config --global user.name "your-git-name"`
- Update config email: `git config --global user.email "your-git-email"`

## Cloning the repository (copy all files into your pc - do once after installation)
1. Create a folder in your pc where you want the project
2. Windows CMD to change directory to intended folder: cd /d (folderPath)
3. git clone `https://github.com/smleong-ntu/ct2003-t2.git`
4. git remote -v

ensure your output shows this. if it does not, go to step 5
```
origin  https://github.com/smleong-ntu/ct2003-t2.git (fetch)
origin  https://github.com/smleong-ntu/ct2003-t2.git (push)
```

5. `OPTIONAL` git remote set-url origin (repo link)
> SETUP COMPLETE

## Uploading your changes
**repeatable**
> Make it a habit to pull before attempting to commit and push
1. git add (filename)
2. git commit -m "(message)"
3. git pull origin (main) --rebase
4. git push -u origin (main)

[spec]: https://entuedu-my.sharepoint.com/:w:/r/personal/c260010_e_ntu_edu_sg/Documents/CT2004%20-%20Software%20Engineering/Specifications.docx?d=wd730d71b5d8048e4a4afa840136bc5c6&csf=1&web=1&e=73DPu9
[ghconfig]: https://youtu.be/r8jQ9hVA2qs?si=aVXLdkI_udhQNqrS&t=186
[profile]: https://github.com/smleong-ntu