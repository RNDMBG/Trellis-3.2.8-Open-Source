var OWNER = "RNDMBG";
var REPO = "Trellis-3.2.8-Open-Source";

function buildNumber(tag) {
  if (!tag) return -1;
  var m = /build[-_ ]?(\d+)/i.exec(tag);
  return m ? parseInt(m[1], 10) : -1;
}

function renderRelease(rel) {
  var tag = rel.tag_name || "";
  var n = buildNumber(tag);
  var label = n >= 0 ? "Latest: 3.2.8 (build " + n + ")" : "Latest: " + tag;
  document.getElementById("versionLine").textContent = label;
  var assets = rel.assets || [];
  for (var i = 0; i < assets.length; i++) {
    var name = assets[i].name || "";
    if (name.slice(-4).toLowerCase() === ".apk") {
      document.getElementById("dlBtn").href = assets[i].browser_download_url;
      break;
    }
  }
}

function renderFallback() {
  document.getElementById("versionLine").textContent = "Couldn't reach GitHub just now — the releases page has every build.";
}

try {
  fetch("https://api.github.com/repos/" + OWNER + "/" + REPO + "/releases/latest", {
    headers: { "Accept": "application/vnd.github+json" }
  }).then(function (r) {
    if (!r.ok) throw new Error("http " + r.status);
    return r.json();
  }).then(renderRelease).catch(renderFallback);
} catch (e) {
  renderFallback();
}
