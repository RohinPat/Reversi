In order to run the program using the main you must follow the following argument format:
    First argument is an int board size which is optional - defaults to a board size of 4
    Second argument is a string for player 1(Black player) - Either "ai" or "human"
        If you choose an ai you must follow it up with a strategy listed below.
    Third argument is a string for player 2(White player) - Either "ai" or "human"
        If you choose an ai you must follow it up with a strategy listed below

    You may put as many stratagies as you would like as the code will automatically wrap the strategies in as many trytwo as needed.  Each trytwo basically uses the first strategy and then moves on to the next one if the strategy tries to pass

    If nothing is put in it will default to a human vs human board with a board size of 4

    Strategy Args:
        avoidcorners
        capturecorners
        capturemost

        avoidtilesnexttocorners
        minimax
        mostpointsgained
        playcorners

    Example full arguments
        human human - two human players with default board size 4
        ai avoidtilesnexttocorners capturecorners minimax human - one ai player that plays the 3 given strategies vs a human player with the default board size 4
        8 ai minimax ai capturemost - one ai running minimax strategy vs another ai using capture most strategy that is on a board size of 8
        12 human human - two human players with a board size of 12

Overview
This document provides an overview of the features and functionalities implemented in our recent project. The project focused on integrating a range of features from our system with those from a provider's system, ensuring seamless operation and enhanced usability.

Features Successfully Implemented
    - Providers View Integration:
        Achieved full integration of the provider's view with our system.
        Ensured compatibility and seamless functionality.
    - View Customizations:
        Successfully incorporated the provider's color schemes into our system.
        Implemented 'invalid move' pop-ups as per the provider's design.
    - Gameplay Enhancements:
        Integrated key commands such as 'M' for move and 'P' for pass from the provider's system.
            We used 'M' for move but use 'space' for pass which was different from their design so now each interface uses their intended models.
        Enabled an end-game screen similar to the provider's design, enhancing the user experience.
    - Strategy Implementations:
        Successfully incorporated all the provider's strategies into our system.
        Enabled the use of our strategies within the provider's view.
        Facilitated the use of the provider's strategies within our view.
        Implemented the ability to combine our strategies with those of the provider's.
Limitations and Non-Functional Features
    As of the current version, all planned features and integrations have been successfully implemented and are functioning as intended.