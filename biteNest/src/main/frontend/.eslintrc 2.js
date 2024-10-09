module.exports = {
  extends: ["react-app", "react-app/jest", "plugin:prettier/recommended"],
  plugins: ["prettier"],
  rules: {
    "prettier/prettier": "error",
  },
  settings: {
    react: {
      version: "detect", // Detect the React version
    },
  },
};
