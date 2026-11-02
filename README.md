# WiFi Analyzer

A lightweight WiFi network dashboard for monitoring nearby access points and evaluating channel congestion.

## What it is

This project is a front-end prototype that simulates a WiFi analyzer dashboard. It shows:

- the connected network
- current signal strength
- nearby access points
- a recommended channel for reducing interference

## Run it locally

Because this is a static website, there is no build step required.

Option 1: open `index.html` directly in your browser.

Option 2: serve it locally:

```bash
cd Wifi-analyzer
python3 -m http.server 8000
```

Then open:

```text
http://localhost:8000
```

## Customize the scanner data

Edit the `mockNetworks` array inside `index.html` to change the simulated WiFi data.

## Future improvements

- real Android WiFi API integration
- RSSI history charts
- channel overlap analysis
- dark/light theme toggle
- export scan reports


