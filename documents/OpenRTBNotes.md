# Terms

#### Target Price (Soft Floor) [Reference](https://bit.ly/2VDitXf)

This is the minimum amount a publisher is willing to accept for their ad
request.

#### Bid Floor (Hard Floor) [Reference](https://bit.ly/2VDitXf)

This is the minimum amount a publisher is willing to accept for their ad
request. If an ad source provides a bid below this bid floor it will be
thrown out and not be eligible to win the auction.

Spec 2.3: The minimum bid for this impression expressed in CPM (Cost per
mile, hence 1000)

#### Banner->pos

Integer position of the ad in the screen.

#### SeatID

https://bit.ly/2LRwlr2
https://bit.ly/2LN018Q
The unique ID of the bidder seat to which this bid responseClosed applies, 
such as a DSPClosed's account ID for the buyerClosed.
Bidder is also known as Demand Side Platform (DSP)

## Spec Change form 2.3 to 2.5
https://bit.ly/3bTJv1l

## Specs helper
- https://bit.ly/36If4tY
- https://bit.ly/3bTJv1l

### AdExchange Info

![RTB Process](https://www.pmg.com/wp-content/uploads/2018/04/Screen-Shot-2018-04-23-at-10.20.52-AM.png)

### Publisher object
The Publisher object describes the seller of the ad space (for the App or Site) in which the ad will be displayed.
Reference : https://bit.ly/36If4tY

#### https://bit.ly/36odjSU
Media buyers and advertising agencies can choose to “get a seat” on Ad Exchanges in order to be involved in real-time bidding marketplace. Just like in stock exchange, there are “seats” on an Ad Exchange occupied by different buyers (ad networks, independent agencies/brands). These “seats” can be viewed as a membership or direct relationships between advertisers and the ad exchange, sometimes bypassing DSPs and ATDs. Nonetheless, Ad Exchange seat holders are still not guaranteed to have a full informational control over the inventory they buy.
Having a seat at the Ad Exchange allows advertisers to have a better 
insight to the publisher’s inventory before they decide to bid. 
Some Ad Exchanges may have limited access and even charge a fee for 
a seat at the auction. DSPs may pay the Ad Exchange approximately 10% of 
the media buy as a fee for the “seat”.
Taking part in RTB auction requires the submission of the reply to the bid request from the publishers. Such bid responses contain Seat ID (the buyer’s ID), bid price, and target URL (for further identification).

Ad Exchange will match any Deal IDs for you with the Seat ID assigned to you in the system. On the other side of the system, publishers also have “seats”.

# How advertise is served to the client with Bid Response?

### 4.3.1 Markup Served on the Win Notice
In this method, ad markup is returned to the exchange is via the win notice. 
In this case, the response body of the win notice call 
(i.e., invoking the bid.nurl attribute) contains the ad markup 
and only the ad markup; there must be no other-structured data 
in the response body. Using this method, the bid.adm attribute must be omitted.

### 4.3.2 Markup Served in the Bid
In this method, ad markup is returned directly in the bid itself. 
This is accomplished via the bid.adm attribute.
 If both the adm attribute and win notice return data, 
 the adm contents will take precedence.

# Links

- Reference : https://protocol.bidswitch.com/index.html
- In RTB, will the bid with the highest CPM always win? If not, what are
  the other factors? https://bit.ly/3bNJ2Pd

- What are some bidding strategies when buying media through a DSP /
  RTB? https://bit.ly/35chgtf

- In RTB, will the bid with the highest CPM always win? If not, what are
  the other factors? https://bit.ly/3bNJ2Pd

- Why do some DSPs have a higher QPS than others (queries per second)?
  Does this enable these DSPs to have an overall broader reach?
  https://bit.ly/2KNb29v

- How does RTB determine the winner of multiple high bids?
  https://bit.ly/2VIEJip

- In RTB, are (hard and soft) floor prices known to the DSP?
  https://bit.ly/2yWEFm7

- How are bidding conflicts avoided in a real-time bidding (RTB)
  environment with multiple ad exchanges and networks?
  https://bit.ly/3aKuCOy

- How do ad exchanges and real-time bidding platforms work?
  https://bit.ly/3eW0suS
- Which is better, SSP or DSP? https://bit.ly/2xmo3nw
- How does SSP work with DSP? https://bit.ly/3d1dsgR
- What does it mean to have a seat on an ad exchange? https://bit.ly/2LQaDDY
- RTB Steps ? https://bit.ly/2zz72rx | https://www.quora.com/What-is-real-time-bidding-RTB/answer/Gareth-Paul-Jones | https://bit.ly/2MaOQa7
- How does open RTB integration work between a publisher and an SSP? https://bit.ly/2XcEXPI


>To start the process SSP sends a bid request to DSP (it occurs in real-time bidding environments). It's a signal out to a DSP indicating that SSP has an impression for sale, along with the inventory parameters.
>DSP in its turn, after getting the bid request sends back a bid response (it occurs in real-time bidding environments). This bid response contains info about the price, the creative ID and participant ID.
>All the interactions between DSP and SSP occur through Ad Exchanges (it's some kind of middleman between tech platforms) and via RTB.

- **(Process) Can anyone explain me how the DSP, Ad exchange and SSP works? https://bit.ly/3d402Rg**


# Bid Response Examples

```javascript
{
  "id": "1234567890", // bid response id
  "bidid": "abc1123",  // bid request id
  "cur": "USD",
  "seatbid": [
    {
      "seat": "512",
      "bid": [
        {
          "id": "1",
          "impid": "102",
          "price": 9.43,
          "nurl": "http://adserver.com/winnotice?impid=102",
          "iurl": "http://adserver.com/pathtosampleimage",
          "adomain": [
            "advertiserdomain.com"
          ],
          "cid": "campaign111",
          "crid": "creative112",
          "attr": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            12
          ]
        }
      ]
    }
  ]
}
```