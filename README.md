# EatClub Technical Challenge

**endpoints:**

/deals

/peak-deals

***

**Potential Issues:**
1. For the deals API, I'm assuming the intended behaviour for a deal without a start/end or open/close time is that it's always available as long as the restaurant is open.
2. For the peak deals API, I'm measuring the length of the peak in 15 minute increments; first measurement at 12am, last at 11:45pm. If the peak is ongoing at the end of the day it will say it ended at 11:45pm, in retrospect that may be confusing.
3. Also for the peak deals API, if no peak is found (so zero deals all day) it returns 12:00am, 12:00am to indicate no peak exists.


