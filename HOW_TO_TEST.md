# How to Test the Recipe Manager App 🧪

## Super Simple Testing Guide - Follow These EXACT Steps!

---

## 🚀 **STEP 1: Open the Project in Android Studio**

### **1.1 Launch Android Studio**
1. **Find Android Studio**:
   - Look in your Windows Start Menu
   - Type "Android Studio" in search
   - Click the green Android Studio icon

2. **Wait for Welcome Screen**:
   - You'll see "Android Studio" splash screen
   - Then a window saying "Welcome to Android Studio"
   - This takes about 10-20 seconds

### **1.2 Open the Project**
1. **Click "Open"**:
   - On welcome screen, you'll see 3 big buttons
   - Click the first one that says **"Open"**
   - A file browser window opens

2. **Navigate to Project**:
   - In the file browser, type or navigate to:
     ```
     C:\Users\lenovo\OneDrive\Desktop\miniproject
     ```
   - You should see folders like: `app`, `gradle`, and files like `build.gradle`, `README.md`
   - Click on the **miniproject folder** (it should be highlighted)
   - Click **"OK"** button at bottom right

3. **Wait for Gradle Sync**:
   - Android Studio will start loading the project
   - At the **bottom of the screen**, you'll see a progress bar
   - It says "Gradle: Syncing..." or "Building Gradle project info"
   - **This takes 2-5 minutes** - be very patient!
   - You'll know it's done when:
     - The bottom bar says "Gradle sync finished in X seconds" OR
     - You see a green checkmark ✓ OR
     - The progress bar disappears

**⚠️ If You See Red Errors**:
1. Look at the **top toolbar** of Android Studio
2. Find the **elephant icon** 🐘 (says "Sync Project with Gradle Files" when you hover)
3. Click it
4. Wait again for 2-5 minutes

---

## 📱 **STEP 2: Set Up Your Testing Device**

You have TWO options - pick ONE:

---

### **OPTION A: Use a Real Android Phone** ⭐ (RECOMMENDED - Much Faster!)

#### **A1: Enable Developer Mode**
1. **On your Android phone**:
   - Open **Settings** app (gear icon)
   - Scroll all the way down
   - Tap **"About phone"** or **"About device"**
   - Find **"Build number"** (might be under "Software information")
   - **Tap "Build number" 7 times rapidly**
   - You'll see a message: "You are now a developer!" or "Developer mode enabled"

#### **A2: Enable USB Debugging**
1. **Go back to main Settings**:
   - Press back button
   - You should now see a new option: **"Developer options"** or **"Developer settings"**
   - Tap it

2. **Turn on USB Debugging**:
   - Scroll to find **"USB debugging"**
   - Flip the switch to **ON** (it turns blue or green)
   - A popup appears warning you - click **"OK"** or **"Allow"**

#### **A3: Connect Your Phone**
1. **Get a USB cable**:
   - Use the charging cable that came with your phone
   - Plug the small end into your phone
   - Plug the USB end into your computer

2. **Allow USB Debugging**:
   - On your phone screen, a popup appears
   - It says "Allow USB debugging?"
   - Check the box "Always allow from this computer"
   - Tap **"Allow"** or **"OK"**

3. **Verify Connection in Android Studio**:
   - Look at the **very top** of Android Studio
   - Near the green triangle ▶️ button
   - You should see a dropdown that shows your phone's name
   - Example: "Samsung Galaxy S21" or "Xiaomi Redmi Note"
   - If you see this, **YOU'RE CONNECTED!** ✅

---

### **OPTION B: Use Android Emulator** (If you don't have a phone)

#### **B1: Open Device Manager**
1. **In Android Studio**, look at the **right edge** of the window
2. You'll see vertical tabs/buttons
3. Click the one that looks like a **phone icon** (says "Device Manager" when you hover)
4. A panel opens on the right side

#### **B2: Create a Virtual Device**
1. **If you see NO devices listed**:
   - Click the **"Create Device"** button (big plus icon)
   - A wizard window opens

2. **Choose Hardware**:
   - You'll see phone models on the left: Pixel, Nexus, etc.
   - **Click "Pixel 4"** (recommended)
   - Click **"Next"** button at bottom right

#### **B3: Download System Image**
1. **Select System Image Tab**:
   - You'll see a list of Android versions
   - Look for **"S"** (API 31) or **"R"** (API 30)
   - Next to it, you'll see a **"Download"** link

2. **Click "Download"**:
   - A popup shows download progress
   - **This takes 5-15 minutes** depending on your internet
   - A progress bar shows percentage
   - When done, it says "Download finished"
   - Click **"Finish"**

3. **Complete Setup**:
   - The system image is now selected (has a checkmark)
   - Click **"Next"**
   - You'll see a summary screen
   - Click **"Finish"**

#### **B4: Start the Emulator**
1. **Back in Device Manager**:
   - You now see your Pixel 4 device listed
   - Next to it, there's a **green triangle** ▶️ button
   - Click the **green triangle**

2. **Wait for Boot**:
   - A new window opens (the emulated phone screen)
   - You'll see Android boot animation
   - **Takes 1-3 minutes** for first boot
   - When ready, you'll see the Android home screen with apps

---

## ▶️ **STEP 3: Build and Run the App**

### **3.1 Click the Run Button**
1. **Look at the TOP toolbar** in Android Studio
2. Find the **bright green triangle** ▶️ button (it's quite large)
3. It says "Run 'app'" when you hover over it
4. **Click this button**

**Keyboard Shortcut**: Press `Shift + F10` together

### **3.2 Select Deployment Target**
1. **A popup window appears** titled "Select Deployment Target"
2. You'll see:
   - **Your phone name** (if connected via USB) OR
   - **Your emulator** (e.g., "Pixel 4 API 31")
3. **Click on your device** to select it
4. Click **"OK"** button

### **3.3 Wait for Build**
1. **Bottom of Android Studio** shows build progress:
   - "Building 'miniproject'..."
   - Progress bar moving
   - **First build takes 1-3 minutes**
   - **Subsequent builds**: 30 seconds

2. **You'll Know It's Done When**:
   - Bottom bar says "BUILD SUCCESSFUL"
   - The app automatically opens on your phone/emulator!
   - You see the Recipe Manager app launch screen

---

## ✅ **WHAT YOU SHOULD SEE**

When the app opens successfully, you should see:

### **The Main Screen**:
- **At the very top**: A white search box with gray text "Search recipes..."
- **Next to it (right side)**: An orange round button with a shuffle/random icon
- **Below search**: Colored pill-shaped buttons (chips): "Chicken", "Beef", "Seafood", "Vegetarian", etc.
- **Main area**: A grid of recipe cards (2 columns)
  - Each card shows:
    - A recipe image (food photo)
    - Recipe name below it
    - Category below that (e.g., "Chicken")
- **At the bottom**: Navigation bar with 2 icons:
  - **Search icon** (magnifying glass) - selected/highlighted
  - **Favorites icon** (star)

**✅ If you see this**: The app is working! Continue to tests!

**❌ If you see errors**: Jump to "Troubleshooting" section at bottom

---

## 🧪 **DETAILED TESTS - Follow in Exact Order!**

---

### **TEST 1: Search Recipes** ⏱️ 2 minutes

#### **What You're Testing**: That the app can search for recipes from the internet

#### **Exact Steps**:

**Step 1**: Look at your screen - you should already see chicken recipes (loaded automatically when app opened)

**Step 2**: **Tap the search box** at the top
   - The box is white with gray text "Search recipes..."
   - Tap directly on it
   - Your phone keyboard appears

**Step 3**: **Type**: `pasta`
   - Use your phone keyboard
   - Type all lowercase: p-a-s-t-a
   - As you type, recipes start to change

**Step 4**: **Wait 1-2 seconds**
   - The app is searching
   - Recipes update automatically
   - You should see pasta dishes (Spaghetti, Lasagna, etc.)

**Step 5**: **Clear the search**
   - Tap the search box again
   - Press backspace to delete all letters
   - Or tap the X icon if it appears

**Step 6**: **Try another search**: Type `chicken`
   - Different recipes appear now!

**✅ PASS**: Recipes change based on what you type
**❌ FAIL**: Nothing happens, or you see "No recipes found"

**What It Proves**: App can connect to the internet and fetch recipe data

---

### **TEST 2: Filter by Category** ⏱️ 2 minutes

#### **What You're Testing**: That category buttons work to filter recipes

#### **Exact Steps**:

**Step 1**: **Clear the search box** (make it empty)

**Step 2**: **Look below the search box**
   - You'll see colored oval buttons (called "chips")
   - They say: Chicken, Beef, Seafood, Vegetarian, Dessert, Pasta, Pork, Breakfast

**Step 3**: **Tap "Seafood"**
   - Find the chip that says "Seafood"
   - Tap it once
   - **It becomes darker orange** (showing it's selected)

**Step 4**: **Wait 1 second**
   - Recipes update
   - You now see only seafood recipes (fish, shrimp, etc.)

**Step 5**: **Tap "Dessert"**
   - "Seafood" becomes light again (deselected)
   - "Dessert" becomes dark (selected)
   - Sweet recipes appear (cakes, puddings)

**Step 6**: **Tap "Dessert" again**
   - The chip becomes light (deselected)
   - Recipes go back to default (chicken)

**Step 7**: **Try "Breakfast"**
   - Should see breakfast foods

**✅ PASS**: Each category shows different recipes
**❌ FAIL**: Categories don't change recipes

**What It Proves**: Filtering system works

---

### **TEST 3: View Recipe Details** ⏱️ 2 minutes

#### **What You're Testing**: That you can see full recipe information

#### **Exact Steps**:

**Step 1**: **On the main screen**, look at the grid of recipe cards

**Step 2**: **Choose ANY recipe card**
   - Pick one that looks good!
   - **Tap anywhere on the card** (image or text)

**Step 3**: **New screen opens**
You should see:
   - **Big image** at the very top (takes up top third of screen)
   - **Star button** at top-right corner (floating on image)
   - **Recipe name** in big bold text
   - **Category and country** in smaller text (e.g., "Chicken • Italian")
   - **"Ingredients" heading** in bold
   - **List of ingredients** with bullet points (•)
     - Example: "• 200g - Pasta"
   - **"Instructions" heading** in bold
   - **Cooking steps** in paragraph form

**Step 4**: **Scroll down**
   - Swipe up on the screen to scroll
   - Read all ingredients
   - Keep scrolling to see instructions

**Step 5**: **Check for "Watch Video" button**
   - Some recipes have it, some don't
   - If present, it's a blue button saying "Watch Video"

**✅ PASS**: All recipe info is clearly visible
**❌ FAIL**: Missing ingredients, instructions, or layout is broken

**What It Proves**: Recipe detail screen displays correctly

---

### **TEST 4: Add Recipe to Favorites** ⏱️ 1 minute

#### **What You're Testing**: That "favorite" button works and saves to phone

#### **Exact Steps**:

**Step 1**: **You're still on a recipe detail screen**
   - If not, tap any recipe card to open one

**Step 2**: **Look at top-right corner**
   - You'll see a **floating round button**
   - It has a **STAR icon** on it
   - The star is **OUTLINE/EMPTY** (not filled)

**Step 3**: **Tap the star button once**

**Step 4**: **Watch what happens**:
   - Star button **fills with yellow color**
   - A small message appears at bottom: **"Added to favorites!"**
   - Message disappears after 2 seconds

**Step 5**: **Scroll down to bottom**
   - You now see NEW sections that weren't there before:
   - **"My Notes" heading**
   - A **text box** (says "Add your personal notes here...")
   - **"My Rating" heading**
   - **5 empty stars** (can be tapped)
   - **"Save Changes" button** (blue)

**✅ PASS**: Star fills, toast message appears, notes section shows
**❌ FAIL**: Nothing happens when tapping star

**What It Proves**: App can save recipes to phone's database

---

### **TEST 5: Add Notes and Rating** ⏱️ 2 minutes

#### **What You're Testing**: That you can add personal notes and rate recipes

#### **Exact Steps**:

**Step 1**: **You're still on the same recipe**
   - With the star filled (favorited)
   - Scrolled down to notes section

**Step 2**: **Tap the notes text box**
   - The box says "Add your personal notes here..."
   - Tap directly on it
   - Keyboard appears

**Step 3**: **Type a note**:
   ```
   This looks delicious! Will try for mom's birthday.
   ```
   - Type exactly as shown (or any text you want)

**Step 4**: **Close the keyboard**
   - Press the back button OR
   - Tap anywhere outside the text box

**Step 5**: **Give it a rating**
   - Look at the 5 stars under "My Rating"
   - **Tap the 4th star from the left**
   - You should see **4 stars turn yellow** (filled)
   - The 5th star stays gray (empty)

**Step 6**: **Save your changes**
   - Find the **"Save Changes"** button (blue button)
   - **Tap it**

**Step 7**: **Watch for confirmation**
   - A message appears at bottom: **"Changes saved!"**

**✅ PASS**: Notes save, stars fill, confirmation message shows
**❌ FAIL**: Can't type, stars don't work, no confirmation

**What It Proves**: App can update saved recipes

---

### **TEST 6: View Favorites List** ⏱️ 1 minute

#### **What You're Testing**: That saved recipes appear in favorites tab

#### **Exact Steps**:

**Step 1**: **Press the BACK button** on your phone
   - Physical back button OR
   - On-screen back arrow (top-left if there)
   - You return to the main recipe grid

**Step 2**: **Look at the VERY BOTTOM** of the screen
   - You'll see the navigation bar
   - Two icons: Search (magnifying glass) and Favorites (star)

**Step 3**: **Tap the "Favorites" icon** (star on the right)
   - The star icon highlights/changes color
   - Screen changes to "Favorites" tab

**Step 4**: **You should see**:
   - **Title at top**: "My Favorites (1)" - showing count
   - **Your saved recipe** displayed as a card
   - The card shows:
     - **Small image on left** (thumbnail)
     - **Recipe name** on right
     - **Category** below name
     - **4 yellow stars** (your rating!)
     - **Your notes text**: "📝 This looks delicious!..."

**✅ PASS**: Recipe appears with notes and rating
**❌ FAIL**: Empty screen or "No favorites yet" message

**What It Proves**: Database saves and retrieves favorites correctly

---

### **TEST 7: Edit a Favorite** ⏱️ 2 minutes

#### **What You're Testing**: That you can update notes and ratings

#### **Exact Steps**:

**Step 1**: **On the Favorites screen**, tap your saved recipe card
   - Tap anywhere on it
   - Detail screen opens

**Step 2**: **Verify star is filled**
   - Top-right star button should be **yellow/filled**
   - If empty, something is wrong!

**Step 3**: **Scroll down** to notes section

**Step 4**: **Change the rating**
   - Currently shows 4 stars (from before)
   - **Tap the 5th star**
   - All 5 stars now turn yellow (5-star rating!)

**Step 5**: **Edit the notes**
   - Tap in the notes text box
   - Go to end of text
   - Add more text:
     ```
     Actually, this is PERFECT! Can't wait to try it!
     ```

**Step 6**: **Save changes**
   - Tap **"Save Changes"** button
   - See confirmation: "Changes saved!"

**Step 7**: **Go back to favorites list**
   - Press back button
   - OR tap "Favorites" at bottom again

**Step 8**: **Check the card**
   - Should now show **5 stars** (updated!)
   - Notes preview shows your new text

**✅ PASS**: Rating changed to 5 stars, notes updated
**❌ FAIL**: Changes didn't save

**What It Proves**: Database UPDATE operation works

---

### **TEST 8: Delete a Favorite (Swipe Method)** ⏱️ 2 minutes

#### **What You're Testing**: Swipe-to-delete gesture

#### **Exact Steps**:

**Step 1**: **Make sure you have 1+ favorites**
   - If you deleted it, add another one first
   - Go to Search tab, pick a recipe, favorite it

**Step 2**: **On Favorites tab**, look at your saved recipe card

**Step 3**: **Place finger on the card**
   - Touch anywhere on the recipe card
   - Don't lift your finger yet

**Step 4**: **Swipe LEFT** (or RIGHT)
   - Slide your finger across the screen
   - Move left (toward the left edge)
   - Or right (toward right edge)
   - Lift your finger when it's half way across

**Step 5**: **Confirmation dialog appears**
   - A popup box shows:
   - Title: "Delete Recipe"
   - Message: "Are you sure you want to delete 'Recipe Name' from your favorites?"
   - Two buttons:
     - **"Delete"** (red or prominent)
     - **"Cancel"**

**Step 6**: **Tap "Cancel"**
   - Dialog disappears
   - Recipe card returns to normal position
   - Recipe is still there (not deleted)

**Step 7**: **Swipe again** (left or right)
   - Dialog appears again

**Step 8**: **This time, tap "Delete"**
   - Dialog closes
   - Recipe card **smoothly disappears** (animated)
   - If it was your only favorite:
     - Empty state appears
     - Shows: "No favorites yet" with subtitle

**✅ PASS**: Swipe works, confirmation shows, delete removes recipe
**❌ FAIL**: Swipe doesn't do anything, or recipe doesn't delete

**What It Proves**: Database DELETE operation works, swipe gestures work

---

### **TEST 9: Random Recipe Button** ⏱️ 1 minute

#### **What You're Testing**: "I'm Feeling Lucky" feature

#### **Exact Steps**:

**Step 1**: **Go to Search tab**
   - Tap "Search" icon at bottom (magnifying glass)

**Step 2**: **Clear the search box** (make it empty)

**Step 3**: **Look at top-right of screen**
   - Next to the search box
   - You'll see an **orange round button** (floating action button)
   - It has a **shuffle/dice icon** or **random icon**

**Step 4**: **Tap this button**

**Step 5**: **Wait 1-2 seconds**

**Step 6**: **Recipe detail screen opens**
   - Shows a random recipe
   - Could be ANY recipe from the database
   - You won't know what it is until it loads!
   - Surprise!

**Step 7**: **Try it again**
   - Press back to return to search
   - Tap the random button again
   - You get a DIFFERENT random recipe

**✅ PASS**: Each tap shows a random recipe
**❌ FAIL**: Nothing happens or shows error

**What It Proves**: Random API endpoint works

---

### **TEST 10: Pull to Refresh** ⏱️ 1 minute

#### **What You're Testing**: Pull-down gesture to reload

#### **Exact Steps**:

**Step 1**: **On Search tab**, see the recipe grid

**Step 2**: **Pull down from top**
   - Put your finger at the very top of the recipe list
   - NOT on the search box, but on the recipes
   - Pull down (drag downward)
   - You'll see a **circular spinner** appear

**Step 3**: **Release your finger**
   - The spinner keeps spinning
   - Recipes reload from API
   - Spinner disappears

**Step 4**: **Recipes are refreshed**
   - Same recipes show (since search didn't change)
   - But data was reloaded from internet

**✅ PASS**: Spinner appears and recipes reload
**❌ FAIL**: Nothing happens when pulling

**What It Proves**: SwipeRefreshLayout works

---

### **TEST 11: Screen Rotation** ⏱️ 1 minute

#### **What You're Testing**: That data persists when rotating screen

#### **Exact Steps**:

**Step 1**: **Search for something**
   - Type `pizza` in search box
   - See pizza recipes appear

**Step 2**: **Rotate your phone**
   - Turn phone from vertical (portrait) to horizontal (landscape)
   - OR if using emulator:
     - Look for rotate buttons in emulator controls
     - Or press `Ctrl + Left Arrow`

**Step 3**: **Check what's still there**:
   - Search box still says "pizza"
   - Pizza recipes still displayed
   - Nothing was lost!

**Step 4**: **Rotate back to vertical**
   - Turn phone upright again
   - Data still there!

**✅ PASS**: Data stays after rotation
**❌ FAIL**: Screen goes blank or app crashes

**What It Proves**: ViewModel preserves state correctly

---

### **TEST 12: Offline Mode** ⏱️ 3 minutes

#### **What You're Testing**: That favorites work without internet

#### **Exact Steps**:

**Step 1**: **Add 2-3 recipes to favorites**
   - Go to Search tab
   - Tap different recipes
   - Favorite each one (tap star)
   - Verify they appear in Favorites tab

**Step 2**: **Turn OFF your internet**

   **On Real Phone**:
   - Open phone's **Settings**
   - Tap **"WiFi"** or **"Connections"**
   - Toggle WiFi to **OFF**
   - Also turn off mobile data if you have it

   **On Emulator**:
   - While emulator is running
   - Click the **"..." (three dots)** button at bottom of emulator
   - Panel opens - click **"Settings"** tab
   - Find **"WiFi"** option
   - Click to **disable** it

**Step 3**: **Try to search (should fail)**
   - Go to Search tab
   - Type anything in search
   - Wait a few seconds
   - **You should see**:
     - Empty screen OR
     - "No recipes found" OR
     - Toast message: "Failed to load recipes" or similar

**Step 4**: **Check favorites (should still work!)**
   - Tap **"Favorites"** tab at bottom
   - **Your saved recipes are STILL THERE!**
   - All 2-3 recipes show perfectly
   - Images, names, notes, ratings - all there!

**Step 5**: **Open a favorite recipe**
   - Tap one of your favorites
   - Detail screen opens
   - Everything displays perfectly
   - Can read ingredients, instructions, notes

**Step 6**: **Edit a favorite (offline)**
   - Change the rating
   - Edit the notes
   - Tap "Save Changes"
   - It saves successfully!

**Step 7**: **Turn internet back ON**
   - Go back to Settings
   - Turn WiFi back ON
   - Return to app

**Step 8**: **Verify search works again**
   - Go to Search tab
   - Search for something
   - Recipes appear again!

**✅ PASS**: Favorites work perfectly offline, search needs internet
**❌ FAIL**: Favorites don't show or app crashes

**What It Proves**: Room database works independently of network

---

## 📊 **FINAL RESULTS CHECKLIST**

Mark each test as you complete it:

- [ ] ✅ **Test 1**: Search recipes - Works!
- [ ] ✅ **Test 2**: Category filters - Works!
- [ ] ✅ **Test 3**: Recipe details - Works!
- [ ] ✅ **Test 4**: Add to favorites - Works!
- [ ] ✅ **Test 5**: Notes and rating - Works!
- [ ] ✅ **Test 6**: View favorites - Works!
- [ ] ✅ **Test 7**: Edit favorites - Works!
- [ ] ✅ **Test 8**: Delete favorite - Works!
- [ ] ✅ **Test 9**: Random recipe - Works!
- [ ] ✅ **Test 10**: Pull to refresh - Works!
- [ ] ✅ **Test 11**: Screen rotation - Works!
- [ ] ✅ **Test 12**: Offline mode - Works!

**If all 12 are checked ✅: YOUR APP IS 100% WORKING!** 🎉🎉🎉

---

## 🐛 **TROUBLESHOOTING - Common Problems**

### **Problem 1: Build Fails - "Resource not found" error**

**Error shows**: Missing `ic_launcher` or similar

**Solution**:
1. The AndroidManifest.xml has been fixed to use default Android icons
2. If you still see this:
   - In Android Studio: **Build → Clean Project**
   - Then: **Build → Rebuild Project**
   - Wait for rebuild to finish
   - Try running again

---

### **Problem 2: No Recipes Show on First Open**

**Error**: Empty screen or "No recipes found"

**Solution**:
1. **Check internet**: Make sure phone/emulator has WiFi
2. **Wait longer**: First API call takes 5-10 seconds
3. **Pull down** on the recipe grid to refresh
4. **Try searching**: Type "chicken" manually
5. **Check date/time**: Make sure phone's date and time are correct

**To test if internet works**:
- Open Chrome or browser on the device
- Try loading google.com
- If it doesn't load, fix internet first

---

### **Problem 3: Images Don't Load**

**Error**: Gray boxes instead of food photos

**Solution**:
1. **Check internet** (most common cause)
2. **Wait longer**: Images load after recipe data
3. **Scroll down and back up**: Triggers image reload
4. On **slow internet**: Takes 10-20 seconds per image

---

### **Problem 4: "Cannot Add to Favorites" or Star Doesn't Work**

**Error**: Star button doesn't fill, or app crashes

**Solution**:
1. **Check Logcat for errors**:
   - In Android Studio: View → Tool Windows → Logcat
   - Look for RED lines
   - Search for "FavoriteRecipe" or "Room" errors

2. **Try a different recipe**: Some recipes might have incomplete data

3. **Reinstall app**:
   - In Android Studio: Run → Stop
   - On device: Long-press app icon → Uninstall
   - In Android Studio: Run again (reinstalls fresh)

---

### **Problem 5: App Crashes on Launch**

**Error**: "Recipe Manager has stopped" or black screen

**Solution**:
1. **Check Logcat** (View → Tool Windows → Logcat)
   - Look for "FATAL EXCEPTION" in red
   - Read the error message

2. **Common causes**:
   - **No internet when fetching default recipes**: Connect to WiFi
   - **AndroidManifest error**: Make sure icons are fixed (see Problem 1)
   - **Missing dependencies**: Sync Gradle again (elephant icon)

3. **Nuclear option**:
   - Build → Clean Project
   - Build → Rebuild Project
   - Run → Stop
   - Close Android Studio
   - Reopen Android Studio
   - Open project again
   - Run again

---

### **Problem 6: Favorites Don't Save**

**Error**: Add to favorites, but when checking Favorites tab - it's empty

**Solution**:
1. **Check if database was created**:
   - In Android Studio: View → Tool Windows → App Inspection
   - Select "Database Inspector" tab
   - Select your device
   - Expand "recipe_manager_db"
   - Click "favorite_recipes" table
   - Check if rows are there

2. **If no database**:
   - There's a code issue
   - Check Logcat for Room errors

---

### **Problem 7: Emulator is Very Slow**

**Error**: Emulator takes forever to do anything

**Solution**:
1. **Close other apps** on your computer
2. **Use a real phone instead** (much faster!)
3. **Increase emulator RAM**:
   - Device Manager → Your device → Edit (pencil icon)
   - Increase RAM to 2048 MB or 4096 MB
   - Click Finish
   - Restart emulator

---

### **Problem 8: Gradle Sync Fails**

**Error**: Red errors in Gradle sync, can't build

**Solution**:
1. **Check internet connection** (Gradle downloads libraries)
2. **Try again**: Click sync elephant icon 🐘
3. **Clear Gradle cache**:
   - File → Invalidate Caches → Invalidate and Restart
   - Wait for Android Studio to restart
   - Sync again

4. **Check `build.gradle` files**: Make sure they weren't accidentally edited

---

## 📸 **What Success Looks Like**

### **Search Screen** ✅
- White search bar at top
- Orange random button (right side)
- Colored category chips below
- Grid of recipe cards (2 columns)
- Each card: Image + Name + Category
- Bottom navigation (Search & Favorites)

### **Favorites Screen** ✅
- Title: "My Favorites (X)" with count
- List of saved recipes (1 column)
- Each card: Thumbnail (left) + Name + Category + Stars + Notes preview
- If empty: "No favorites yet" message with subtitle

### **Detail Screen** ✅
- Large image at top (covers screen width)
- Star button floating on image (top-right)
- Recipe name (large, bold)
- Category and origin (e.g., "Chicken • Italian")
- Ingredients section with bullets
- Instructions section
- If favorited: Notes box + Rating stars + Save button at bottom
- Optional: "Watch Video" button (if recipe has video)

---

## ⏱️ **Testing Time Summary**

| Test Type | Time Needed |
|-----------|-------------|
| Quick smoke test | 5 minutes |
| Core features (Tests 1-8) | 15 minutes |
| All features (Tests 1-12) | 20-25 minutes |
| With troubleshooting | 30-40 minutes |

---

## 🎯 **Professional QA Checklist**

For thorough testing, also check:

### **Functionality**
- [ ] Search with special characters (@, #, etc.)
- [ ] Search with very long text (50+ characters)
- [ ] Add 10+ favorites
- [ ] Edit multiple favorites
- [ ] Delete all favorites
- [ ] Open app, close app, reopen (data persists?)

### **Edge Cases**
- [ ] No internet on first open
- [ ] Search with no results ("xyzabc")
- [ ] Rapidly tap buttons (no crashes?)
- [ ] Background app (press Home) then return
- [ ] Phone call comes in (app survives?)
- [ ] Low storage (app still saves favorites?)

### **UI/UX**
- [ ] All text is readable
- [ ] Buttons are tappable (not too small)
- [ ] Images load properly
- [ ] No overlapping text
- [ ] Colors are pleasant
- [ ] Loading spinners appear when expected
- [ ] Error messages are helpful

---

## 📝 **Bug Reporting Template**

If you find a bug, report it like this:

```
**Bug**: [Short description]

**Steps to reproduce**:
1. Do this
2. Then do this
3. Bug happens

**Expected**: What should happen
**Actual**: What actually happens
**Device**: Phone model or emulator
**Android version**: e.g., Android 12
**Screenshot**: (if possible)
```

---

## 🎉 **Congratulations!**

If you completed all 12 tests and they all passed:

✅ **Your Recipe Manager app is PRODUCTION-READY!**

You've successfully tested:
- Network API integration (TheMealDB)
- Local database (Room)
- CRUD operations (Create, Read, Update, Delete)
- UI components (RecyclerView, Fragments, Navigation)
- Gestures (swipe, pull-to-refresh)
- Configuration changes (rotation)
- Offline functionality

**What's Next?**
1. Show it to friends and family
2. Add more recipes to your favorites
3. Try cooking one of the recipes!
4. Extend with new features (dark mode, sharing, etc.)

---

**Testing Complete! Great job!** 🎊

Need help? Check:
- **DEVELOPMENT_GUIDE.md** - For code explanations
- **README.md** - For architecture details
- **Logcat** in Android Studio - For error messages
