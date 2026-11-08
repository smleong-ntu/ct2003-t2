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

## Stage 3 Features

**Happy Hour**
* At the start of each turn, until it has been used, the game asks `HappyHour is available. Activate?` (`1` Yes, `2` No). It can be used once per game.
* It lasts the turn it is switched on and the next turn.
* Orders taken while it is on cost 80% of their price. The VIP discount comes first, then Happy Hour: a VIP Burger costs $15.00 → $13.50 → $10.80. The price is locked when the order is taken, so it doesn't change when Happy Hour ends.
* Customers still waiting (in the queue, seated, or waiting for food) recover 15 satisfaction on each active turn, up to 100.

**Combo meal**
* The menu adds a Salad + Pasta combo: 90% of the two prices, ($9.00 + $18.00) × 0.9 = $24.30, and 3 preparation units (1 + 2).
* With the combo, the menu has four items (Salad, Burger, Pasta, Salad + Pasta), so customer `id` orders item `(id - 1) % 4`. The Critic still orders Pasta. Customer 4 would order the combo, but customer 4 is the Critic, so with the current arrivals nobody orders it.

**Host**
* A fourth staff member the manager doesn't control. During Arrivals, the Host seats the first customer in the queue at the first free table. If nobody is waiting or no table is free, it prints `Invalid: >>Seat Customer<< ...` and does nothing.
* The Waiter can still seat a customer in the same turn.

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

### Stage 3
Replayed at commit `e11ba76`. Each turn now starts with the Happy Hour prompt (`1` Yes, `2` No) until Happy Hour is used, and the Host seats arrivals before Manager Selection. Both change which keys come next, so the Stage 2 key lists don't work in Stage 3.

| Result | Choices | Outcome |
|---|---|---|
| Victory | Host seats arrivals. Waiter picks Serve, else Take Order, else Seat, else Wait. Chef picks Prepare, else Wait. Happy Hour never used | 5 paid, $67.50, satisfaction 87.80 |
| Victory with Happy Hour | Same choices, with Happy Hour switched on at turn 4, when the VIP arrives. The VIP's Burger is ordered that turn for $10.80 | 5 paid, $64.80, satisfaction 88.80 |
| Defeat | Waiter and Chef both pick Wait every turn. Happy Hour never used | 0 paid, $0.00, 2 unhappy departures |

Happy Hour costs $2.70 of revenue and raises average satisfaction by 1.00.

```sh
# Victory
echo 2 2 1 2 3 2 2 4 2 2 2 1 2 3 2 2 4 2 2 2 1 2 4 1 2 3 2 2 2 1 2 4 1 2 3 2 2 2 1 2 3 2 2 4 2 2 2 1 2 3 2 2 4 2 | java -cp bin main.gameflow.RestaurantRushApp
# Victory with Happy Hour on turn 4
echo 2 2 1 2 3 2 2 4 2 1 2 1 3 2 4 2 2 1 4 1 3 2 2 1 4 1 3 2 2 1 3 2 4 2 2 1 3 2 4 2 | java -cp bin main.gameflow.RestaurantRushApp
# Defeat (the Stage 1 keys, with 2 = "No Happy Hour" at the start of each turn)
echo 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 2 4 2 | java -cp bin main.gameflow.RestaurantRushApp
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

### Stage 2 → Stage 3
Compare: [`stage-2...stage-3`][cmp-2-3]. Happy Hour and the combo are commits `1baeda7` to `49e3318` and `f0d1c82` to `8c91bf5`. The Policy step and the `STAGE_3` setting are `9338615` to `7674222`. The Host is `2adb364` to `7e6efdf` and `0cb8d42`.

**New files**

| File | What it adds |
|---|---|
| `src/main/restaurant/HappyHour.java` | Once-per-game pricing policy: 2 turns, 80% price, +15 recovery. Keeps its own countdown (`activate()`, then `tick()` at each turn start). Gets the recovery as an `IntConsumer` callback, so it doesn't depend on `Restaurant` |
| `src/main/menu/ComboMeal.java` | Subclass of `MenuItem` made of two base items: 90% of their combined price and the sum of their preparation units. The Chef, Waiter, Cashier and `Order` handle it with no changes |
| `src/main/gameflow/turns/PolicyStep.java` | New first step of each turn. Asks the manager whether to switch Happy Hour on, until it has been used |
| `src/main/staff/Host.java` | Subclass of `Staff`. `findWaitingCustomer()` picks the first customer in the queue (FIFO) and `findAvailableTable()` the first free table |
| `src/main/staff/task/HostTask.java` | Base class for the Host's tasks |
| `src/main/staff/task/SeatAutoTask.java` | The Host's automatic seating. `isTaskValid()` needs a waiting customer and a free table; `execute()` seats through `Restaurant.seatCustomer`, like the Waiter |

**Changed files**

| File | Change and reason |
|---|---|
| `src/main/restaurant/Restaurant.java` | Owns the `HappyHour`. `placeOrder` applies the customer's discount, then Happy Hour, then locks the price. `start()` calls `happyHour.tick()`. New `applyRecovery(amount)` for customers still waiting. `applyEndOfTurnWaiting()` removed, because `end()` already runs each customer's `endTurn()` |
| `src/main/menu/Menu.java` | Keeps combos apart from base items (`getCombos()`, `getAllItems()`). Rejects duplicate names and combos whose parts aren't base items |
| `src/main/restaurant/RestaurantView.java` | Prints `Happy hour active. Turns remaining N` and `Happy hour has ended` |
| `src/main/events/OnRestaurant.java` | New `PolicyUpdated` event, sent when Happy Hour switches on, counts down or ends |
| `src/main/gamesetting/GameDataType.java` | New `STAGE_3` setting: the Policy step first, a Host on the staff, and the Salad + Pasta combo on the menu. Same arrivals as Stage 2 |
| `src/main/gameflow/turns/ArrivalStep.java` | After the arrival, runs the Host's first valid task, so the Host seats before Manager Selection |
| `src/main/gameflow/turns/TurnStep.java`, `src/main/gameflow/turns/ManagerStep.java` | One shared input reader for every step (moved out of `ManagerStep`), so piped key lists work with two steps that ask for input |
| `src/main/staff/task/TaskType.java` | The Host's task list: Seat Customer, Wait |
| `src/main/gameflow/turns/TurnStartView.java` | Adds an empty `displayPolicy()` hook (no output yet) |
| `src/main/restaurant/WaitingQueue.java` | Comment only |
| `src/main/gameflow/RestaurantRushApp.java` | Starts the game with `STAGE_3` instead of `STAGE_2` |
| `README.md` | Stage 3 features, the Stage 3 test runs and this comparison |

Host seating, as seen during gameplay:

```
Queue: [Customer 1]
Executing SeatAutoTask: Seat Customer done by >> Host [ID:0] <<
```


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
[cmp-2-3]: https://github.com/smleong-ntu/ct2003-t2/compare/stage-2...stage-3
[jdk]: https://www.oracle.com/asean/java/technologies/downloads/#jdk25-windows
[jdk-setup]: https://www.youtube.com/watch?v=jvhL10Fvj4s