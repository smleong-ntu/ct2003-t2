# CT2003 Project Team 2 (2026)
```
ESWAN BIN ESA'RI
LEONG SEE MING
LIM HUIXIN VIVIAN
LOH CHEE JUN
```

## Build and Run Instructions

### Prerequisites
* [Java Development Kit][jdk] (JDK) 25 or higher installed.
* Java binary directory added to your system's [PATH environment variable][jdk-setup].

### How to Build
Open a terminal or command prompt in the root directory of the project and compile the source code by running:
```bash
cd /d C:\Project_Folder_Location
dir /s /b src\*.java > sources.txt
javac -d bin @sources.txt
del sources.txt
```

### How to Run
Once compiled, execute the application from the project root using the following command:
```bash
java -cp bin main.gameflow.RestaurantRushApp
```

Visit [Report][spec] for object diagrams for reference
Request permissions from [smleong-ntu][profile] if not visible

Visit [Presentation][slides] for our group presentation

## Food Rule
Customers are numbered 1-6 in arrival order. Customer `id` orders menu item `(id - 1) % 3`, which gives Salad, Burger, Pasta, Salad, Burger, Pasta.

## Customer Types (Stage 2)
Customer 2 (arrives on turn 4) is a VIP and customer 4 (arrives on turn 10) is a Critic. The other four are regular customers, so there are still six arrivals.

| Customer | Satisfaction loss per waiting turn | Bill | Order |
|---|---|---|---|
| Regular | 8 | Full price | Food rule |
| VIP | 5 | 10% off | Food rule |
| Critic | 12 | Full price | Always the most expensive base item (Pasta, $18.00), ignoring the food rule |

**Critic rule:** the Critic always orders the most expensive base item on the menu. If two items cost the same, the one listed first on the menu wins.

## Test Notes
If anything changes the prompts, the key lists need re-recording.

### Stage 1
Replayed at the `stage-1` tag (`288bf85`).

| Result | Choices | Outcome |
|---|---|---|
| Victory | Waiter picks Serve, else Take Order, else Seat, else Wait. Chef picks Prepare, else Wait | 5 paid, $66.00, satisfaction 79.20 |
| Defeat | Waiter and Chef both pick Wait every turn | 0 paid, $0.00, 2 unhappy departures |

```sh
# Victory
echo 1 2 2 1 3 2 1 2 2 1 3 2 1 2 2 1 4 1 3 2 1 2 2 1 3 2 1 2 2 1 3 2 1 2 2 1 | java -cp bin main.gameflow.RestaurantRushApp
# Defeat
echo 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 | java -cp bin main.gameflow.RestaurantRushApp
```

### Stage 2
Replayed at the `stage-2` tag. Both runs include the VIP and the Critic. The choices are the same as Stage 1, but the Victory keys differ from turn 13 because the Critic (arrives on turn 10) changes the game state.

| Result | Choices | Outcome |
|---|---|---|
| Victory | Waiter picks Serve, else Take Order, else Seat, else Wait. Chef picks Prepare, else Wait | 5 paid, $73.50, satisfaction 75.60 |
| Defeat | Waiter and Chef both pick Wait every turn | 0 paid, $0.00, 2 unhappy departures |

```sh
# Victory
echo 1 2 2 1 3 2 1 2 2 1 3 2 1 2 2 1 4 1 3 2 1 2 2 1 1 1 3 2 2 1 3 2 1 2 2 1 | java -cp bin main.gameflow.RestaurantRushApp
# Defeat (same keys as Stage 1)
echo 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 4 2 | java -cp bin main.gameflow.RestaurantRushApp
```

## Stage Comparisons

### Stage 1 → Stage 2
Compare: [`stage-1...stage-2`][cmp-1-2]. The new customer types are commits `cb1b478` and `8877389`. Commits `d7295d2` to `2bfa2ad` add the menu setting, fixes and display tidy-ups.

**New files**

| File | What it adds |
|---|---|
| `src/main/customer/VIPCustomer.java` | Subclass of `Customer`. Overrides `getWaitingLoss()` (5) and `adjustBill(cents)` (10% off) |
| `src/main/customer/CriticCustomer.java` | Subclass of `Customer`. Overrides `getWaitingLoss()` (12) and `chooseMenuItem(menu)` (always the most expensive base item) |

**Changed files**

| File | Change and reason |
|---|---|
| `src/main/gamesetting/GameDataType.java` | Adds a `STAGE_2` setting (named `stage-2`) whose arrivals on turn 4 and turn 10 create a `VIPCustomer` and a `CriticCustomer`. The brief needs at least one of each, and there are still six arrivals. `DEFAULTS` keeps the Stage 1 arrivals (six regular customers). Both settings now list the menu items (Salad, Burger, Pasta) |
| `src/main/gamesetting/GameData.java` | Adds `menuItems()`, so each setting chooses its own menu instead of `Menu` hard-coding it |
| `src/main/menu/Menu.java` | Takes the list of base items from the setting (`Menu(List<? extends MenuItem>)`) and rejects an empty list. It doesn't import the settings package, so `menu` and `gamesetting` don't depend on each other |
| `src/main/restaurant/Restaurant.java` | Builds the menu from the setting's items. `Restaurant(int)` is removed; `Restaurant(GameData)` is the only constructor |
| `src/main/gamesetting/GameObjective.java` | Shows `N/A (target 60.00)` for average satisfaction when nobody has paid, as the brief asks, instead of `0.00` |
| `src/main/gameflow/turns/ManagerStep.java` | Clear messages for a word (`Not a number`) or an out-of-range number (`There is no task N`). If input runs out, the game stops with a message instead of a stack trace |
| `src/main/gameflow/turns/TurnStartView.java` | The `Tasks:` line lists Waiter then Chef tasks, the same order as the prompts (`LinkedHashMap` instead of `HashMap`) |
| `src/main/customer/Customer.java`, `src/main/staff/Cashier.java` | Comments only: `Customer` is the base class for VIP and Critic, and the Cashier's charge already includes the VIP discount |
| `src/main/gameflow/RestaurantRushApp.java` | Starts the game with `STAGE_2` instead of `DEFAULTS` |
| `README.md` | Customer types, the Critic rule, the Stage 2 test runs and this comparison |

### Stage 3

Replayed at commit `0cb8d42`. 

Stage 3 introduces automatic customer seating through the Host role. 

The Stage 2 replay is no longer valid in Stage 3 because the Host automatically seats customers before Manager Selection. 
This changes the sequence of valid tasks and causes the Stage 2 recorded inputs to fall out of sync with the game state. A new Stage 3 replay must therefore be recorded.


| Result | Choices | Outcome |
|---|---|---|
| Victory | Host auto-seats customers. Waiter picks Serve, else Take Order, else Seat, else Wait. Chef picks Prepare, else Wait. | 5 paid, $67.50, satisfaction 85.60 |
| Defeat | Waiter and Chef both pick Wait every turn | 0 paid, $0.00, 2 unhappy departures |

```sh
# Victory
echo  2 2 1 2 4 2 2 3 2 2 2 1 1 3 2 4 2 2 1 4 1 3 2 2 1 4 1 3 2 4 2 2 1 3 2 2 1 3 2 4 2 | java -cp bin main.gameflow.RestaurantRushApp
# Defeat (same keys as Stage 1)
echo 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 | java -cp bin main.gameflow.RestaurantRushApp
```

## Stage Comparisons

### Stage 2 → Stage 3

**Changed files**

| File | Change and reason |
|---|---|
| `src/main/staff/Host.java` | Subclass of Staff. Added `findWaitingCustomer()` and `findAvailableTable()`. The Host selects the first waiting customer (FIFO) and the first available table for automatic seating |
| `src/main/staff/task/SeatAutoTask.java` | Implemented automatic seating behaviour. Added execute() to seat customers automatically and `isTaskValid()` to validate customer and table availability before seating. |


Verification, observed during gameplay:

```
Queue: [Customer 1]

Executing SeatAutoTask: Seat Customer done by >> Host [ID:0] <<
```

This confirms that the Host automatically seats customers before Manager Selection.


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
[slides]: https://entuedu-my.sharepoint.com/:p:/r/personal/cloh021_e_ntu_edu_sg/Documents/CT2003-t2%20Presentation.pptx?d=w42b66e3a5f66482b829918e75e1e831e&csf=1&web=1&e=4ipo7E
[ghconfig]: https://youtu.be/r8jQ9hVA2qs?si=aVXLdkI_udhQNqrS&t=186
[profile]: https://github.com/smleong-ntu
[cmp-1-2]: https://github.com/smleong-ntu/ct2003-t2/compare/stage-1...stage-2
[jdk]: https://www.oracle.com/asean/java/technologies/downloads/#jdk25-windows
[jdk-setup]: https://www.youtube.com/watch?v=jvhL10Fvj4s