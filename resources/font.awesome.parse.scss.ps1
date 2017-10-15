<#

Input formated as:

500px "\uf26e"
address-book "\uf2b9"
address-book-o "\uf2ba"
address-card "\uf2bb"
address-card-o "\uf2bc"

Input taken from font-awesome-4.7.0\font-awesome-4.7.0\scss\_variables.scss

#>


$dir = "C:\Users\lyuboslav\Desktop\"
$inputFile = "font-awesome-4.7.0.keys"

$enumOutput = "";
$initOutput = "";
$newLine = [System.Environment]::NewLine


Get-Content (Join-Path $dir $inputFile) | %{
   $parts = $_.Split(@(" "), [System.StringSplitOptions]::RemoveEmptyEntries)
   
   $key = $parts[0]
   $value = $parts[1]
   
   $keyParts = $key.Split(@("-"), [System.StringSplitOptions]::RemoveEmptyEntries)
   $key = ""
   $keyParts | %{
      $key += $_.Substring(0, 1).ToUpper()
      $key += $_.Substring(1, $_.Length - 1)
   }
   
   $enumOutput += "      $key,$newLine"
   $initOutput += "         iconMap.Add(Icon.$key, $value);$newLine"
}

Set-Content -Path (Join-Path $dir "$inputFile.enum") $enumOutput
Set-Content -Path (Join-Path $dir "$inputFile.init") $initOutput