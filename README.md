# NonDeveloper_TestAutomation
Automating Selenium without Knowledge of Programming


An attempt at providing selenium functionality with YAML like syntax. A tester who do not have any programming background can write automated test cases based out of Selenium with ease using just YAML like syntax.

For example, to test login to github.com, using selenium on browsers Chrome and Firefox, one may use following markup:

    version: 1.0
    start:
      - page:
          browsers:
            - chrome
            - firefox
          url: https://github.com/login
          change:
            element:
              - input:
                  by-name: login
                  enter-data: test@gmail.com
              - input:
                  by-name: password
                  enter-data: ^4ts%EOSqk7x
              - input:
                  by-id: commit
                  click: true
          check:
            element:
              - input:
                  find-text: "@data-ga-click"
                  compare: "text:Signed in as"
                  print: true


## Folder Structure

    IGGY: The actual project
    linter: The linter for electron to add IGGY development support easier on electron
    
