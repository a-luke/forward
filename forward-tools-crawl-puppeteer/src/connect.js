const puppeteer = require('puppeteer');

async function connect(){
    const browser = await puppeteer.connect({ browserWSEndpoint: 'ws://127.0.0.1:54500/devtools/browser/a00e0e4d-0a11-4f89-883c-fe1516a9525b' , slowMo : 500});
    const page = await browser.newPage();

    const fs = require('fs');
    // In your puppeteer script, assuming the preload.js file is in same folder of our script

    //修改浏览器默认配置
    // const preloadFile = fs.readFileSync('./preload.js', 'utf8');
    // await page.evaluateOnNewDocument(preloadFile);
    var timestamp=new Date().getTime();
    await page.goto('http://weibo.com/p/230444f964de5429d50b0b30e85eafbca2e106');
    var promise1 = page.content();

    Promise.race([promise1]).then(function(value) {
        console.log(value);
        var now=new Date().getTime();
        console.info("-------------------------------------------------------------------------------------");
        console.info(now-timestamp);
        // Both resolve, but promise2 is faster
    });
}

connect();

