# Test for website
**Environment** : java+junit+selenium  
The test site http://eu.wiley.com/WileyCDA/  
Short video with a demonstration of the test https://youtu.be/F7pTgGM8Fpg  
###Test case  
1. Open http://www.wiley.com/WileyCDA/  
Check the following links displayed in top navigation menu  
 - Home  
 - Subjects
 - About Wiley
 - Contact Us
 - Help  
   
2. Check items under Resources sub-header  
 - There are 9 items under resources sub-header
 - Titles are “Students”, “Authors”, “Instructors”, “Librarians”, “Societies”,
“Conferences”, “Booksellers”, “Corporations”, “Institutions  
  
3. Click “Students” item
 - Check that http://www.wiley.com/WileyCDA/Section/id-404702.html url is opened
 - Check that “Students” header is displayed  
  
4. Check “Resources For” menu on the left  
 - 8 items are displayed in the menu
 - Items are “Authorts”, “Librarians”, “Booksellers”, “Instructors”, “Students”, “Societies”, “Corporate Partners”  
  
5. Check “Students” item is selected  
 - “Students” item has different style  
 - “Students” item is not clickable  
  
6. Click “Home” link at the top navigation menu  
  
7. Find “Sign up to receive Wiley updates” line and input field next to it. Do not enter
anything and click arrow button  
 - Check that alert appeared  
 - Check that alert text is “Please enter email address”  
  
8. Enter invalid email (for example without @)  
 - Check that alert appeared  
 - Check that alert text is “Invalid email address.”  
   
9. Find search input in the top of the page.  
Enter “for dummies” to the input field and press search icon next to the input field.  
 - Check that list of items appeared  

10. Click random item link (link with book title)  
 - Check that page with header equal to the title you clicked is displayed  
  
11. Click “Home” link at the top navigation menu  
  
12. Click “Institutions” icon under Resources sub-header  
 - Check http://wileyedsolutions.com/ is opened in new window (or tab)
