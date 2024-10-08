# User stories

Based on
http://github.com/guyroyse/vending-machine-kata

### 1. Accept Coins

_As a vendor_
_I want a vending machine that accepts coins_
_So that I can collect money from the customer_

The vending machine will accept valid coins (nickels, dimes, and quarters) and reject invalid one (pennies).When a
valid coin is inserted the amount of the coin will be added to the current amount and the display will be
updated (use pattern: CREDIT 9.99).
When there are no coins inserted, the machine displays INSERT COIN. Rejected coins are placed in the coin return.

------------
### 2. Accept Coins - corner case

_As a vendor_
_I want a vending machine to reject a coin_
_when internal Coin Cassette is full_

The vending machine has internal Coin Cassette with
dedicated tubes for each coin type. When machine is not serviced properly, tubes can be full.
In such case, when client inserts a valid coin, it should be rejected
and machine should display CASSETTE IS FULL, SORRY.

If the display is
checked again, it will show INSERT COINS or CREDIT, depending on current state.

--------------
### 3. Select Product

_As a vendor_
_I want customers to select products_
_So that I can give them an incentive to put money in the machine_

There are three products: cola for $1.00, chips for $0.50, and candy for $0.65.  When the respective button is pressed
and enough money has been inserted, the product is dispensed and the machine displays THANK YOU.

If the display is
checked again, it will show INSERT COINS and the current amount will be set to $0.00.  If there is not enough money
inserted then the machine displays PRICE and the price of the item and subsequent checks of the display will display
either INSERT COINS or the current amount as appropriate.


-----------
### 4. Make Change

_As a vendor_
_I want customers to receive correct change_
_So that they will use the vending machine again_

When a product is selected that costs less than the amount of money in the machine, then the remaining amount is placed
in the coin return.

-----------
### 5. Storage & emails
_As a venodr_
_I want to be notified via email when machine runs out of product_

Vending machine has one shelve per product, each with 10 items capacity. Full machine contains 10x cola, 10x chips and 10x candy.

Each time when user selects sold out product, machine should do two things:

1. display NO ${productName}, SORRY
1. send email to vendor with supply request