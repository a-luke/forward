const puppeteer = require('puppeteer');
puppeteer.launch({
    headless: false,
    executablePath: 'C:\\Program Files (x86)\\Google\\chrome-win32\\chrome.exe'
}).then(async browser => {
    // Store the endpoint to be able to reconnect to Chromium
    const browserWSEndpoint = browser.wsEndpoint();
    const page = await browser.newPage();
    await page.goto('https://www.baidu.com/');

    const searchValue = await page.$eval('#kw', el => el.value="123qwe");

    await page.close();

    

    browser.disconnect();
    // const page2 = await browser.newPage();
    // await page2.goto('http://blog.csdn.net/leolaurel/article/details/8363430');

    console.info({browserWSEndpoint});
    const browser2 = await puppeteer.connect({browserWSEndpoint});
    const page3 = await browser2.newPage();
    await page3.goto('http://10.0.0.37:8089/admin/offlinecheck/toList.html?currentPage=1&showCount=30');

    

    // other actions...
    await browser.close();
});



