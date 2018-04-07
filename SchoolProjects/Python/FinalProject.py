from tkinter import *

class StudentGradeBook:
    #Creating the GUI
    window = Tk() #Create a window
    window.title("Python Student Grade Book") #Sets title
    window.geometry('400x225')

    #Frames for the GUI
    mainMenuLblFrame = Frame(window)
    mainMenuBtFrame = Frame(window)
    seeStudentsNamesFrame = Frame(window)
    addStudentsNameFrame = Frame(window)
    addGradesFrame = Frame(window)
    addGradeFormFrame = Frame(window)
    checkGradesFrame = Frame(window)

    #Variables
    StudentName = ""
    StudentsList = []

    def __init__(self):
        self.mainMenu()
        self.window.mainloop()

    def mainMenu(self):
        #Setting Main Menu frames to visible, others non-visible
        self.mainMenuLblFrame.pack()
        self.mainMenuBtFrame.pack()
        self.seeStudentsNamesFrame.pack_forget()
        self.addStudentsNameFrame.pack_forget()
        self.addGradesFrame.pack_forget()
        self.addGradeFormFrame.pack_forget()
        self.checkGradesFrame.pack_forget()

        #Main Menu string and label
        MainMenuString = "This is a Student Grade Book Application. Use can see all\n current students, add students, check grades, and add grades.\n"
        lblMainMenu = Label(self.mainMenuLblFrame, text=MainMenuString).grid(row=1, column=1)

        #Buttons and labels
        btSeeStudents = Button(self.mainMenuBtFrame, text="See Students", command=self.seeStudents).grid(row=1, column=1, sticky='we')
        btAddStudents = Button(self.mainMenuBtFrame, text="Add Students", command=self.addStudents).grid(row=2, column=1, sticky='we')
        btCheckGrades = Button(self.mainMenuBtFrame, text="Check Grades", command=self.checkGrades).grid(row=3, column=1, sticky='we')
        btAddGrades = Button(self.mainMenuBtFrame, text="Add Grades", command=self.addGrades).grid(row=4, column=1, sticky='we')
        ###END OF METHOD: mainMenu###

    #Function for the seeStudents button
    def seeStudents(self):
        #Setting seeStudents frames to visible, others non-visible
        self.mainMenuLblFrame.pack_forget()
        self.mainMenuBtFrame.pack_forget()
        self.seeStudentsNamesFrame.pack()
        self.addStudentsNameFrame.pack_forget()
        self.addGradesFrame.pack_forget()
        self.addGradeFormFrame.pack_forget()
        self.checkGradesFrame.pack_forget()

        lblSeeStudents = Label(self.seeStudentsNamesFrame, text="If no names appear, then there are no current students.\nPlease go to Add Students section to add current students.").grid(row=1,column=1)

        #The label for the names
        lblNames = StringVar()
        Label(self.seeStudentsNamesFrame, textvariable=lblNames).grid(row=2,column=1)

        #The string for the names
        namesString=""
        for i in range (0, len(self.StudentsList)):
            namesString += ("%s\n"%self.StudentsList[i].name)

        #Setting the lblNames label
        lblNames.set(namesString)

        #Button to go back to main menu for seeStudentsBtFrame
        btMainMenu = Button(self.seeStudentsNamesFrame, text="Main Menu", command=self.mainMenu).grid(row=3,column=1)
        ###END OF METHOD: seeStudents###


    #Function for the addStudents button
    def addStudents(self):
        #Setting addStudents frames to visible, others non-visible
        self.mainMenuLblFrame.pack_forget()
        self.mainMenuBtFrame.pack_forget()
        self.seeStudentsNamesFrame.pack_forget()
        self.addStudentsNameFrame.pack()
        self.addGradesFrame.pack_forget()
        self.addGradeFormFrame.pack_forget()
        self.checkGradesFrame.pack_forget()

        #The student name entry
        lblStudentName = Label(self.addStudentsNameFrame, text="Student Name: ").grid(row=1,column=1)
        self.StudentName = StringVar()
        self.StudentNameEntry = Entry(self.addStudentsNameFrame, textvariable=self.StudentName)
        self.StudentNameEntry.grid(row=1,column=2)

        #To add some spacing for the form
        lblSpace = Label(self.addStudentsNameFrame, text="\n\n\n").grid(row=2, column=1)

        #Button to go back to main menu
        btMainMenu = Button(self.addStudentsNameFrame, text="Main Menu", command=self.mainMenu).grid(row=3, column=1)
        btSubmit =  Button(self.addStudentsNameFrame, text="Submit", command=self.AddStudentsSubmit).grid(row=3,column=2)
        ###END OF METHOD: addStudents###

    #Function for the checkGrades button
    def checkGrades(self):
        self.mainMenuLblFrame.pack_forget()
        self.mainMenuBtFrame.pack_forget()
        self.seeStudentsNamesFrame.pack_forget()
        self.addStudentsNameFrame.pack_forget()
        self.addGradesFrame.pack_forget()
        self.addGradeFormFrame.pack_forget()
        self.checkGradesFrame.pack()

        #Creating the list of names for the dropdown list
        self.names = []
        if (len(self.StudentsList) == 0): ##If no current students
            self.names.append("No current students")
        else:
            for i in range (0, len(self.StudentsList)):
                self.names.append(self.StudentsList[i].name)

        #Dropdown list to select student
        self.studentSelected = StringVar()
        self.studentSelected.set(self.names[0])
        Label(self.checkGradesFrame, text="Student: ").grid(row=1,column=1)
        studentDropDownList = OptionMenu(self.checkGradesFrame, self.studentSelected, *self.names, command=self.getGrades).grid(row=1, column=2)

        #The labels to print out the grades
        Label(self.checkGradesFrame, text="").grid(row=2, column=1)
        Label(self.checkGradesFrame, text="Assignments: ").grid(row=3, column=1)
        self.lblAssignment = StringVar()
        Label(self.checkGradesFrame, textvariable=self.lblAssignment).grid(row=3, column=2)
        Label(self.checkGradesFrame, text="Quizzes: ").grid(row=4, column=1)
        self.lblQuiz = StringVar()
        Label(self.checkGradesFrame, textvariable=self.lblQuiz).grid(row=4, column=2)
        Label(self.checkGradesFrame, text="Exams: ").grid(row=5, column=1)
        self.lblExam = StringVar()
        Label(self.checkGradesFrame, textvariable=self.lblExam).grid(row=5, column=2)
        Label(self.checkGradesFrame, text="").grid(row=6, column=1)
        Label(self.checkGradesFrame, text="Final: ").grid(row=7, column=1)
        self.lblFinal = StringVar()
        Label(self.checkGradesFrame, textvariable=self.lblFinal).grid(row=7, column=2)

        #Main menu
        Label(self.checkGradesFrame, text="").grid(row=8, column=1)
        btMainMenu = Button(self.checkGradesFrame, text="Main Menu", command=self.mainMenu).grid(row=9, column=1)
    ##END OF METHOD: checkGrades##

    #Method that will get the grades for a student
    def getGrades(self, value):
        studentSelect = value
        studentIndex = -1 #Index for which student the user selected

        #Finding the studentIndex for studentSelect
        for i in range (0, len(self.StudentsList)):
            if self.StudentsList[i].name == studentSelect:
                studentIndex = i

        #to make sure the index is not the default, meaning the name is in the studentsList
        if studentIndex != -1:
            grades = self.StudentsList[studentIndex].calcFinalGrade()
        else:
            grades = [0,0,0,0]

        #Setting the labels to the grade values
        self.lblAssignment.set(grades[0]) #assignment grades
        self.lblQuiz.set(grades[1]) #quiz grades
        self.lblExam.set(grades[2]) #test grades
        self.lblFinal.set(grades[3]) #final grades

    #Function for the addGrades button
    def addGrades(self):
        self.mainMenuLblFrame.pack_forget()
        self.mainMenuBtFrame.pack_forget()
        self.seeStudentsNamesFrame.pack_forget()
        self.addStudentsNameFrame.pack_forget()
        self.addGradesFrame.pack()
        self.addGradeFormFrame.pack()
        self.checkGradesFrame.pack_forget()

        #Tells the user what to do
        strAddGrades = "Please enter the value of the grade, then select which type of grade it is."
        lblAddGrades = Label(self.addGradesFrame, text=strAddGrades).grid(row=1, column=1)

        #Creating the list of names for the dropdown list
        self.names = []
        if (len(self.StudentsList) == 0): ##If no current students
            self.names.append("No current students")
        else:
            for i in range (0, len(self.StudentsList)):
                self.names.append(self.StudentsList[i].name)

        #Dropdown list to select student
        self.studentSelected = StringVar()
        self.studentSelected.set(self.names[0])
        lblStudentDropDown = Label(self.addGradeFormFrame, text="Student: ").grid(row=1,column=1)
        studentDropDownList = OptionMenu(self.addGradeFormFrame, self.studentSelected, *self.names).grid(row=1, column=2)


        #The entry field for the value and type
        lblValue = Label(self.addGradeFormFrame, text="Value: ").grid(row=2,column=1)
        self.gradeValue = StringVar()
        self.gradeValueEntry =  Entry(self.addGradeFormFrame, textvariable=self.gradeValue)
        self.gradeValueEntry.grid(row=2,column=2)

        #Grade Type
        self.gradeType = IntVar()
        rbAssignment = Radiobutton(self.addGradeFormFrame, text="Assignment", variable=self.gradeType, value=0).grid(row=3, column=1)
        rbQuiz = Radiobutton(self.addGradeFormFrame, text="Quiz", variable=self.gradeType, value=1).grid(row=3, column=2)
        rbExam = Radiobutton(self.addGradeFormFrame, text="Exam", variable=self.gradeType, value=2).grid(row=3, column=3)

        #To add some spacing for the form
        lblSpace = Label(self.addGradeFormFrame, text="\n\n\n").grid(row=4, column=1)

        #Main menu and submit buttons
        btMainMenu = Button(self.addGradeFormFrame, text="Main Menu", command=self.mainMenu).grid(row=5, column=1)
        btSubmit =  Button(self.addGradeFormFrame, text="Submit", command=self.AddGradesSubmit).grid(row=5,column=3)
    ##END OF METHOD: AddGrades##

    #Function for the Submit button in the AddGrades frames
    def AddGradesSubmit(self):
        #Getting the grade type, 0=assignment, 1=quiz, 2=exam
        gradeType = self.gradeType.get()
        #Getting the grade value
        gradeValue = float(self.gradeValue.get())
        #Getting the student name
        studentSelect = self.studentSelected.get()
        studentIndex = -1 #Index for which student the user selected

        #Finding the studentIndex for studentSelect
        for i in range (0, len(self.StudentsList)):
            if self.StudentsList[i].name == studentSelect:
                studentIndex = i

        #Checks to make sure the index is in bounds
        if studentIndex != -1:
            if gradeType == 0: #If assignment is selected
                self.StudentsList[studentIndex].assignmentGrades.append(gradeValue)
            elif gradeType == 1: #If quiz is selected
                self.StudentsList[studentIndex].quizGrades.append(gradeValue)
            else: #If exam is selected
                self.StudentsList[studentIndex].testGrades.append(gradeValue)

        self.gradeValueEntry.delete(0,END)
    ##END OF METHOD: AddGradesSubmit##

    #Function for the Submit button in the Add Students frames
    def AddStudentsSubmit(self):
        #Gets the name from the form, and creates a Students object, adds it to the StudentsList list
        name = self.StudentName.get()
        self.StudentsList.append(Students(name))
        #Clears entry field
        self.StudentNameEntry.delete(0,END)
    ##END OF METHOD: AddStudentsSubmit##
#####END OF CLASS: StudentGradeBook#####

class Students:
    #Constructor for Students
    def __init__(self, name):
        if name=="":
            self.name="Unnamed Student"
        else:
            self.name = name

        #Setting the default values
        self.grades = [0, 0, 0, 0] #Holds the assignmentGrades, quizGrades, testGrades, finalGrade for a student
        self.assignmentGrades = [] #The grades for all assignments
        self.quizGrades = [] #The grades for all quizzes
        self.testGrades = [] #The grades for all tests



    #Function to calculate final grade for a student
    def calcFinalGrade(self):
        #variables
        assignmentGradesLen = len(self.assignmentGrades) ####################################
        quizGradesLen = len(self.quizGrades) #############Holds the lengths for the grades###
        testGradesLen = len(self.testGrades) ################################################
        assignmentGradesTotal = 0 ###################################
        quizGradesTotal = 0 #######Holds the total amount of grades##
        testGradesTotal = 0 #########################################

        #Finds the totals for all three type of grades
        for i in range (0, assignmentGradesLen):
            assignmentGradesTotal += float(self.assignmentGrades[i])
        for i in range (0, quizGradesLen):
            quizGradesTotal += float(self.quizGrades[i])
        for i in range (0, testGradesLen):
            testGradesTotal += float(self.testGrades[i])

        #Finds the average for each type of grade
        if assignmentGradesLen != 0:
            self.grades[0] = (assignmentGradesTotal/float(assignmentGradesLen))
        else:
            self.grades[0] = 100
        if quizGradesLen != 0:
            self.grades[1] = (quizGradesTotal/float(quizGradesLen))
        else:
            self.grades[1] = 100
        if testGradesLen != 0:
            self.grades[2] = (testGradesTotal/float(testGradesLen))
        else:
            self.grades[2] = 100

        #Assigns self.grades to variables for easier readability
        finalAssignmentGrade = self.grades[0]
        finalQuizGrade = self.grades[1]
        finalTestGrade = self.grades[2]

        #Calculates final Grade
        self.grades[3] = ((finalAssignmentGrade * 0.40)+(finalQuizGrade * 0.10)+(finalTestGrade*0.50))

        #Return grades
        return self.grades
    ###END OF METHOD: calcFinalGrade###

#####END OF CLASS: Students#####

StudentGradeBook()
