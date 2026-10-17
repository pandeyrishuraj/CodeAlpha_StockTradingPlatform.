# CodeAlpha Stock Trading Platform

A beginner-friendly, console-based Java project that simulates basic stock trading using sample fixed prices.

## Features
- View a small sample stock market.
- Buy shares when sufficient cash is available.
- Sell shares only when they are present in the portfolio.
- View portfolio quantities and market value.
- View available cash balance.
- Validate menu choices, quantities, and stock symbols.

## Requirements
- Java 14 or later (the menu uses modern switch syntax).
- A terminal or Java IDE such as VS Code.

## Run
Open a terminal in the folder containing `StockTradingPlatform.java` and run:

```bash
javac StockTradingPlatform.java
java StockTradingPlatform
```

## Notes
- The starting balance is ₹10,000.
- Stock prices are fixed sample values for demonstration only; this project does not connect to a real stock exchange and does not provide live prices or financial advice.
- Balance and portfolio data are kept in memory and reset when the program restarts.

## Concepts demonstrated
Classes and objects, `Map`/`LinkedHashMap`, loops, methods, input validation, conditionals, and formatted console output.
