# How to Test the Recipe Manager App 🧪

## Super Simple Testing Guide - Follow These Exact Steps!

---

## 🚀 **STEP 1: Open the Project**

1. **Open Android Studio**
   - Double-click the Android Studio icon on your desktop
   - Wait for it to fully load (you'll see the welcome screen)

2. **Open This Project**
   - Click "Open" button
   - Navigate to: `C:\Users\lenovo\OneDrive\Desktop\miniproject`
   - Click "OK"

3. **Wait for Gradle to Sync**
   - You'll see "Gradle Sync" running at the bottom
   - This takes 2-5 minutes (be patient!)
   - When done, you'll see "Gradle sync finished" or a green checkmark

**⚠️ If you see errors**: Click the elephant icon 🐘 at top (says "Sync Project with Gradle Files")

---

## 📱 **STEP 2: Prepare Your Device**

### **Option A: Use a Real Phone** (Recommended - Faster!)

1. **Enable Developer Mode on Your Phone**
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times (yes, really!)
   - You'll see "You are now a developer!"

2. **Enable USB Debugging**
   - Go to Settings → Developer Options
   - Turn ON "USB Debugging"
   - Click "OK" on the warning

3. **Connect Your Phone**
   - Plug USB cable into computer and phone
   - On phone, you'll see "Allow USB Debugging?" → Tap "Allow"
   - Your phone name should appear at the top of Android Studio

### **Option B: Use Emulator** (If no phone available)

1. **Open Device Manager**
   - In Android Studio, click: Tools → Device Manager
   - Or click the phone icon at the top right

2. **Create Virtual Device**
   - Click "Create Device"
   - Choose "Pixel 4" (or any phone)
   - Click "Next"

3. **Download System Image**
   - Choose "S" or "R" (API 30 or 31)
   - Click "Download" next to it
   - Wait for download (takes 5-10 minutes)
   - Click "Next" → "Finish"

4. **Start Emulator**
   - Click the green triangle ▶️ next to your device
   - Wait for emulator to boot (takes 1-2 minutes)
   - You'll see a phone screen appear

---

## ▶️ **STEP 3: Run the App**

1. **Click the Green Triangle**
   - At the very top of Android Studio, you'll see a green triangle ▶️
   - Click it (or press `Shift + F10`)

2. **Select Your Device**
   - A popup appears: "Select Deployment Target"
   - Choose your phone or emulator
   - Click "OK"

3. **Wait for Build**
   - You'll see "Building..." at the bottom
   - First time takes 1-3 minutes
   - When done, app automatically opens on your device!

**✅ SUCCESS**: You should see the Recipe Manager app open with a search bar and recipe cards!

---

## 🧪 **STEP 4: Test All Features** (Follow in Order!)

### **Test 1: Search Recipes** ⏱️ 1 minute

1. **Look at the Screen**
   - You should already see chicken recipes (loaded automatically)
   - See recipe cards in a grid (2 columns)

2. **Type in Search Box**
   - Tap the search box at top (says "Search recipes...")
   - Type: `pasta`
   - Watch recipes update automatically!

3. **Try Another Search**
   - Clear the search box
   - Type: `chicken`
   - Different recipes appear!

**✅ PASS**: Recipes change when you search
**❌ FAIL**: Nothing happens or error message appears

---

### **Test 2: Category Filters** ⏱️ 1 minute

1. **Look Below Search Box**
   - You'll see colored chips: Chicken, Beef, Seafood, etc.

2. **Tap a Category**
   - Tap "Seafood" chip
   - Chip turns darker (selected)
   - Recipes update to show only seafood!

3. **Try Another Category**
   - Tap "Dessert" chip
   - See dessert recipes appear!

4. **Unselect Category**
   - Tap the same chip again
   - Back to default chicken recipes

**✅ PASS**: Recipes filter by category
**❌ FAIL**: Chips don't work or recipes don't change

---

### **Test 3: View Recipe Details** ⏱️ 1 minute

1. **Tap Any Recipe Card**
   - Click on a recipe you like
   - New screen opens!

2. **Look at the Details Screen**
   - You should see:
     - Big recipe image at top
     - Recipe name
     - Ingredients list (with bullets)
     - Cooking instructions
     - Star button (top right)

3. **Scroll Down**
   - Scroll to read all ingredients
   - Scroll to read all instructions

**✅ PASS**: All recipe info shows clearly
**❌ FAIL**: Missing ingredients or instructions

---

### **Test 4: Add to Favorites** ⏱️ 1 minute

1. **Click the Star Button**
   - At top right of detail screen
   - Star should fill in (becomes solid yellow)
   - Toast message appears: "Added to favorites!"

2. **See Notes Section Appear**
   - Scroll down to bottom
   - You now see:
     - "My Notes" text box
     - "My Rating" with 5 stars
     - "Save Changes" button

**✅ PASS**: Star fills in and notes section appears
**❌ FAIL**: Nothing happens when clicking star

---

### **Test 5: Add Notes and Rating** ⏱️ 1 minute

1. **Tap Notes Box**
   - Click on "Add your personal notes here..."
   - Type: `This looks delicious! Mom's birthday dinner idea.`

2. **Tap Stars to Rate**
   - Tap the 4th star
   - Should see 4 yellow stars

3. **Click Save Changes**
   - Tap the "Save Changes" button
   - Toast message: "Changes saved!"

**✅ PASS**: Notes and rating are saved
**❌ FAIL**: Can't type or save button doesn't work

---

### **Test 6: View Favorites** ⏱️ 1 minute

1. **Go Back**
   - Press back button on phone (or back arrow)
   - You're back at search screen

2. **Tap Favorites Tab**
   - At bottom, tap "Favorites" icon (star)
   - New screen opens!

3. **See Your Saved Recipe**
   - You should see the recipe you just saved
   - It shows:
     - Recipe image (smaller, on left)
     - Recipe name
     - Category
     - Your 4-star rating
     - Your notes preview

**✅ PASS**: Recipe appears in favorites with your notes
**❌ FAIL**: Favorites screen is empty

---

### **Test 7: Edit Favorites** ⏱️ 1 minute

1. **Tap the Favorite Recipe**
   - Click on the recipe in your favorites list
   - Detail screen opens

2. **Change the Rating**
   - Scroll down to rating stars
   - Tap the 5th star (give it 5 stars!)

3. **Update the Notes**
   - Add more text: `Actually, this is PERFECT!`
   - Click "Save Changes"

4. **Go Back to Favorites**
   - Press back button
   - Tap "Favorites" at bottom again
   - See: 5 stars now (updated!)

**✅ PASS**: Rating and notes update
**❌ FAIL**: Changes don't save

---

### **Test 8: Delete from Favorites** ⏱️ 1 minute

1. **Go to Favorites Tab**
   - Make sure you're on Favorites screen

2. **Swipe Recipe Card Left**
   - Put finger on recipe card
   - Swipe left (or right)
   - Confirmation dialog appears!

3. **Confirm Delete**
   - Dialog says: "Are you sure you want to delete..."
   - Tap "Delete"
   - Recipe disappears!
   - Empty state appears: "No favorites yet"

**✅ PASS**: Recipe deletes after confirmation
**❌ FAIL**: Swipe doesn't work or recipe doesn't delete

---

### **Test 9: Random Recipe** ⏱️ 30 seconds

1. **Go to Search Tab**
   - Tap "Search" at bottom

2. **Tap the Dice Button**
   - Orange circular button at top right (next to search)
   - Has a shuffle/dice icon

3. **Random Recipe Opens**
   - A surprise recipe detail screen appears!
   - You don't know what it'll be!

**✅ PASS**: Random recipe opens
**❌ FAIL**: Nothing happens or error

---

### **Test 10: Pull to Refresh** ⏱️ 30 seconds

1. **On Search Screen**
   - Make sure you're on search tab
   - See the recipe grid

2. **Pull Down**
   - Put finger at top of recipe list
   - Pull down and release
   - You'll see a spinning circle

3. **Watch Refresh**
   - Recipes reload
   - Spinner disappears

**✅ PASS**: Recipes refresh
**❌ FAIL**: Nothing happens

---

### **Test 11: Screen Rotation** ⏱️ 30 seconds

1. **Search for Something**
   - Search for "pizza"
   - See pizza recipes

2. **Rotate Your Phone**
   - Turn phone sideways (landscape mode)
   - If using emulator: Press rotate button in emulator controls

3. **Check Data Stays**
   - Pizza recipes should still be there!
   - Search text still says "pizza"

4. **Rotate Back**
   - Turn phone upright again
   - Data still there!

**✅ PASS**: Data persists through rotation
**❌ FAIL**: Screen goes blank or crashes

---

### **Test 12: Offline Mode** ⏱️ 2 minutes

1. **Add 2-3 Recipes to Favorites**
   - Search and save a few recipes
   - Make sure they appear in Favorites tab

2. **Turn Off Internet**
   - **Real Phone**: Settings → WiFi → Turn OFF
   - **Emulator**: Extended controls → Settings → WiFi → Disable

3. **Try Search (Should Fail)**
   - Go to Search tab
   - Search for anything
   - Error toast appears: "No internet connection" or no results

4. **Check Favorites (Should Work!)**
   - Go to Favorites tab
   - Your saved recipes are still there!
   - Tap one to see details
   - Everything works perfectly!

5. **Turn Internet Back On**
   - Re-enable WiFi

**✅ PASS**: Favorites work offline, search doesn't
**❌ FAIL**: App crashes or favorites don't load

---

## 📊 **Final Checklist**

After testing everything, check off what works:

- [ ] ✅ Search recipes by name
- [ ] ✅ Filter by category chips
- [ ] ✅ View recipe details
- [ ] ✅ Add to favorites (star button)
- [ ] ✅ Add notes to favorite
- [ ] ✅ Rate favorite recipe
- [ ] ✅ Save notes/rating changes
- [ ] ✅ View all favorites
- [ ] ✅ Edit favorites
- [ ] ✅ Delete favorite (swipe)
- [ ] ✅ Random recipe button
- [ ] ✅ Pull to refresh
- [ ] ✅ Screen rotation (data persists)
- [ ] ✅ Offline mode (favorites work)

**If all checked ✅: APP WORKS PERFECTLY! 🎉**

---

## 🐛 **Common Problems & Solutions**

### **Problem 1: App Won't Build**

**Error Message**: "Gradle sync failed" or red text in bottom panel

**Solution**:
1. Click the elephant icon 🐘 at top
2. Wait for sync to finish
3. If still fails: File → Invalidate Caches → Restart

---

### **Problem 2: No Recipes Show**

**Error Message**: Empty screen or "No recipes found"

**Solution**:
1. Check internet connection (WiFi or mobile data)
2. Try pulling down to refresh
3. Try searching for "chicken" manually
4. Check phone's date/time is correct

---

### **Problem 3: Images Don't Load**

**Error Message**: Gray boxes instead of recipe images

**Solution**:
1. Check internet connection
2. Wait a few seconds (images load slowly on slow internet)
3. Scroll down and back up (triggers reload)

---

### **Problem 4: Can't Add to Favorites**

**Error Message**: Star doesn't fill or app crashes

**Solution**:
1. Close and reopen the app
2. Try a different recipe
3. Check Logcat for errors (View → Tool Windows → Logcat)

---

### **Problem 5: App Crashes**

**Error Message**: "Recipe Manager has stopped"

**Solution**:
1. Check Logcat (View → Tool Windows → Logcat)
2. Look for red error text
3. Usually means: no internet when searching OR database error
4. Try: Uninstall app from device, then run again from Android Studio

---

## 📸 **What Success Looks Like**

### **Search Screen**:
- Grid of recipe cards (2 columns)
- Each card shows image + name + category
- Search bar at top
- Category chips below search
- Orange random recipe button

### **Favorites Screen**:
- List of saved recipes (1 column)
- Each shows image, name, category, rating, notes preview
- Title shows count: "My Favorites (3)"
- Empty state if nothing saved

### **Detail Screen**:
- Big image at top
- Star button (top right)
- Recipe name and category
- Ingredients list with bullets
- Instructions in paragraphs
- Notes/rating section (if favorited)

---

## ⏱️ **Total Testing Time**

- Quick test (all features once): **10 minutes**
- Thorough test (try edge cases): **20 minutes**
- Professional QA test: **30 minutes**

---

## 🎓 **What Each Test Checks**

| Test | What It Proves |
|------|----------------|
| Search | API integration works |
| Categories | Filter logic works |
| Details | Data parsing works |
| Add Favorite | Database INSERT works |
| Notes/Rating | Database UPDATE works |
| View Favorites | Database SELECT works |
| Delete | Database DELETE works |
| Random | API random endpoint works |
| Pull-to-Refresh | UI refresh works |
| Rotation | ViewModel state works |
| Offline | Room database works |

---

## 🎉 **You're Done!**

If all tests pass, your app is working perfectly!

**Next Steps**:
1. Show it to friends/family
2. Add more recipes to favorites
3. Try different searches
4. Explore all categories
5. Have fun cooking! 🍳

---

## 📞 **Need Help?**

1. Check **DEVELOPMENT_GUIDE.md** for troubleshooting
2. Check **README.md** for architecture info
3. Look at Logcat for error messages
4. Check that internet is working

---

**Testing completed? Give yourself a high-five! 🙌**

**The app is production-ready and fully functional!** ✅
