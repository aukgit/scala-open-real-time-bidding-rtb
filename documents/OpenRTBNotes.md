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

Publisher id

# Links

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

>To start the process SSP sends a bid request to DSP (it occurs in real-time bidding environments). It's a signal out to a DSP indicating that SSP has an impression for sale, along with the inventory parameters.
>DSP in its turn, after getting the bid request sends back a bid response (it occurs in real-time bidding environments). This bid response contains info about the price, the creative ID and participant ID.
>All the interactions between DSP and SSP occur through Ad Exchanges (it's some kind of middleman between tech platforms) and via RTB.

- **(Process) Can anyone explain me how the DSP, Ad exchange and SSP works? https://bit.ly/3d402Rg**


# Bid Response Examples

```javascript
{
  "id": "1234567890",
  "bidid": "abc1123",
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