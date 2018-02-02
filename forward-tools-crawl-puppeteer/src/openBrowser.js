const puppeteer = require('puppeteer');
puppeteer.launch({
    headless: false,
    executablePath: 'C:\\Program Files (x86)\\Google\\chrome-win32\\chrome.exe'
}).then(async browser => {
    const browserWSEndpoint = browser.wsEndpoint();
    console.info({browserWSEndpoint});
})