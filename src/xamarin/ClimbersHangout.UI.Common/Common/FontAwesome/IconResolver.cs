﻿using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.UI.Common.Common.FontAwesome {
   internal class IconResolver {
      private static Dictionary<Icon, string> iconMap;

      static IconResolver() {
         iconMap = new Dictionary<Icon, string>();
         InitializeIconMap();
      }

      public static string Resolve(Icon icon) {
         if (!iconMap.ContainsKey(icon)) {
            throw new ArgumentException(String.Format("{0} icon not supported.", icon));
         }

         return iconMap[icon];
      }

      private static void InitializeIconMap() {
         iconMap.Add(Icon._500px, "\uf26e");
         iconMap.Add(Icon.AddressBook, "\uf2b9");
         iconMap.Add(Icon.AddressBookO, "\uf2ba");
         iconMap.Add(Icon.AddressCard, "\uf2bb");
         iconMap.Add(Icon.AddressCardO, "\uf2bc");
         iconMap.Add(Icon.Adjust, "\uf042");
         iconMap.Add(Icon.Adn, "\uf170");
         iconMap.Add(Icon.AlignCenter, "\uf037");
         iconMap.Add(Icon.AlignJustify, "\uf039");
         iconMap.Add(Icon.AlignLeft, "\uf036");
         iconMap.Add(Icon.AlignRight, "\uf038");
         iconMap.Add(Icon.Amazon, "\uf270");
         iconMap.Add(Icon.Ambulance, "\uf0f9");
         iconMap.Add(Icon.AmericanSignLanguageInterpreting, "\uf2a3");
         iconMap.Add(Icon.Anchor, "\uf13d");
         iconMap.Add(Icon.Android, "\uf17b");
         iconMap.Add(Icon.Angellist, "\uf209");
         iconMap.Add(Icon.AngleDoubleDown, "\uf103");
         iconMap.Add(Icon.AngleDoubleLeft, "\uf100");
         iconMap.Add(Icon.AngleDoubleRight, "\uf101");
         iconMap.Add(Icon.AngleDoubleUp, "\uf102");
         iconMap.Add(Icon.AngleDown, "\uf107");
         iconMap.Add(Icon.AngleLeft, "\uf104");
         iconMap.Add(Icon.AngleRight, "\uf105");
         iconMap.Add(Icon.AngleUp, "\uf106");
         iconMap.Add(Icon.Apple, "\uf179");
         iconMap.Add(Icon.Archive, "\uf187");
         iconMap.Add(Icon.AreaChart, "\uf1fe");
         iconMap.Add(Icon.ArrowCircleDown, "\uf0ab");
         iconMap.Add(Icon.ArrowCircleLeft, "\uf0a8");
         iconMap.Add(Icon.ArrowCircleODown, "\uf01a");
         iconMap.Add(Icon.ArrowCircleOLeft, "\uf190");
         iconMap.Add(Icon.ArrowCircleORight, "\uf18e");
         iconMap.Add(Icon.ArrowCircleOUp, "\uf01b");
         iconMap.Add(Icon.ArrowCircleRight, "\uf0a9");
         iconMap.Add(Icon.ArrowCircleUp, "\uf0aa");
         iconMap.Add(Icon.ArrowDown, "\uf063");
         iconMap.Add(Icon.ArrowLeft, "\uf060");
         iconMap.Add(Icon.ArrowRight, "\uf061");
         iconMap.Add(Icon.ArrowUp, "\uf062");
         iconMap.Add(Icon.Arrows, "\uf047");
         iconMap.Add(Icon.ArrowsAlt, "\uf0b2");
         iconMap.Add(Icon.ArrowsH, "\uf07e");
         iconMap.Add(Icon.ArrowsV, "\uf07d");
         iconMap.Add(Icon.AslInterpreting, "\uf2a3");
         iconMap.Add(Icon.AssistiveListeningSystems, "\uf2a2");
         iconMap.Add(Icon.Asterisk, "\uf069");
         iconMap.Add(Icon.At, "\uf1fa");
         iconMap.Add(Icon.AudioDescription, "\uf29e");
         iconMap.Add(Icon.Automobile, "\uf1b9");
         iconMap.Add(Icon.Backward, "\uf04a");
         iconMap.Add(Icon.BalanceScale, "\uf24e");
         iconMap.Add(Icon.Ban, "\uf05e");
         iconMap.Add(Icon.Bandcamp, "\uf2d5");
         iconMap.Add(Icon.Bank, "\uf19c");
         iconMap.Add(Icon.BarChart, "\uf080");
         iconMap.Add(Icon.BarChartO, "\uf080");
         iconMap.Add(Icon.Barcode, "\uf02a");
         iconMap.Add(Icon.Bars, "\uf0c9");
         iconMap.Add(Icon.Bath, "\uf2cd");
         iconMap.Add(Icon.Bathtub, "\uf2cd");
         iconMap.Add(Icon.Battery, "\uf240");
         iconMap.Add(Icon.Battery0, "\uf244");
         iconMap.Add(Icon.Battery1, "\uf243");
         iconMap.Add(Icon.Battery2, "\uf242");
         iconMap.Add(Icon.Battery3, "\uf241");
         iconMap.Add(Icon.Battery4, "\uf240");
         iconMap.Add(Icon.BatteryEmpty, "\uf244");
         iconMap.Add(Icon.BatteryFull, "\uf240");
         iconMap.Add(Icon.BatteryHalf, "\uf242");
         iconMap.Add(Icon.BatteryQuarter, "\uf243");
         iconMap.Add(Icon.BatteryThreeQuarters, "\uf241");
         iconMap.Add(Icon.Bed, "\uf236");
         iconMap.Add(Icon.Beer, "\uf0fc");
         iconMap.Add(Icon.Behance, "\uf1b4");
         iconMap.Add(Icon.BehanceSquare, "\uf1b5");
         iconMap.Add(Icon.Bell, "\uf0f3");
         iconMap.Add(Icon.BellO, "\uf0a2");
         iconMap.Add(Icon.BellSlash, "\uf1f6");
         iconMap.Add(Icon.BellSlashO, "\uf1f7");
         iconMap.Add(Icon.Bicycle, "\uf206");
         iconMap.Add(Icon.Binoculars, "\uf1e5");
         iconMap.Add(Icon.BirthdayCake, "\uf1fd");
         iconMap.Add(Icon.Bitbucket, "\uf171");
         iconMap.Add(Icon.BitbucketSquare, "\uf172");
         iconMap.Add(Icon.Bitcoin, "\uf15a");
         iconMap.Add(Icon.BlackTie, "\uf27e");
         iconMap.Add(Icon.Blind, "\uf29d");
         iconMap.Add(Icon.Bluetooth, "\uf293");
         iconMap.Add(Icon.BluetoothB, "\uf294");
         iconMap.Add(Icon.Bold, "\uf032");
         iconMap.Add(Icon.Bolt, "\uf0e7");
         iconMap.Add(Icon.Bomb, "\uf1e2");
         iconMap.Add(Icon.Book, "\uf02d");
         iconMap.Add(Icon.Bookmark, "\uf02e");
         iconMap.Add(Icon.BookmarkO, "\uf097");
         iconMap.Add(Icon.Braille, "\uf2a1");
         iconMap.Add(Icon.Briefcase, "\uf0b1");
         iconMap.Add(Icon.Btc, "\uf15a");
         iconMap.Add(Icon.Bug, "\uf188");
         iconMap.Add(Icon.Building, "\uf1ad");
         iconMap.Add(Icon.BuildingO, "\uf0f7");
         iconMap.Add(Icon.Bullhorn, "\uf0a1");
         iconMap.Add(Icon.Bullseye, "\uf140");
         iconMap.Add(Icon.Bus, "\uf207");
         iconMap.Add(Icon.Buysellads, "\uf20d");
         iconMap.Add(Icon.Cab, "\uf1ba");
         iconMap.Add(Icon.Calculator, "\uf1ec");
         iconMap.Add(Icon.Calendar, "\uf073");
         iconMap.Add(Icon.CalendarCheckO, "\uf274");
         iconMap.Add(Icon.CalendarMinusO, "\uf272");
         iconMap.Add(Icon.CalendarO, "\uf133");
         iconMap.Add(Icon.CalendarPlusO, "\uf271");
         iconMap.Add(Icon.CalendarTimesO, "\uf273");
         iconMap.Add(Icon.Camera, "\uf030");
         iconMap.Add(Icon.CameraRetro, "\uf083");
         iconMap.Add(Icon.Car, "\uf1b9");
         iconMap.Add(Icon.CaretDown, "\uf0d7");
         iconMap.Add(Icon.CaretLeft, "\uf0d9");
         iconMap.Add(Icon.CaretRight, "\uf0da");
         iconMap.Add(Icon.CaretSquareODown, "\uf150");
         iconMap.Add(Icon.CaretSquareOLeft, "\uf191");
         iconMap.Add(Icon.CaretSquareORight, "\uf152");
         iconMap.Add(Icon.CaretSquareOUp, "\uf151");
         iconMap.Add(Icon.CaretUp, "\uf0d8");
         iconMap.Add(Icon.CartArrowDown, "\uf218");
         iconMap.Add(Icon.CartPlus, "\uf217");
         iconMap.Add(Icon.Cc, "\uf20a");
         iconMap.Add(Icon.CcAmex, "\uf1f3");
         iconMap.Add(Icon.CcDinersClub, "\uf24c");
         iconMap.Add(Icon.CcDiscover, "\uf1f2");
         iconMap.Add(Icon.CcJcb, "\uf24b");
         iconMap.Add(Icon.CcMastercard, "\uf1f1");
         iconMap.Add(Icon.CcPaypal, "\uf1f4");
         iconMap.Add(Icon.CcStripe, "\uf1f5");
         iconMap.Add(Icon.CcVisa, "\uf1f0");
         iconMap.Add(Icon.Certificate, "\uf0a3");
         iconMap.Add(Icon.Chain, "\uf0c1");
         iconMap.Add(Icon.ChainBroken, "\uf127");
         iconMap.Add(Icon.Check, "\uf00c");
         iconMap.Add(Icon.CheckCircle, "\uf058");
         iconMap.Add(Icon.CheckCircleO, "\uf05d");
         iconMap.Add(Icon.CheckSquare, "\uf14a");
         iconMap.Add(Icon.CheckSquareO, "\uf046");
         iconMap.Add(Icon.ChevronCircleDown, "\uf13a");
         iconMap.Add(Icon.ChevronCircleLeft, "\uf137");
         iconMap.Add(Icon.ChevronCircleRight, "\uf138");
         iconMap.Add(Icon.ChevronCircleUp, "\uf139");
         iconMap.Add(Icon.ChevronDown, "\uf078");
         iconMap.Add(Icon.ChevronLeft, "\uf053");
         iconMap.Add(Icon.ChevronRight, "\uf054");
         iconMap.Add(Icon.ChevronUp, "\uf077");
         iconMap.Add(Icon.Child, "\uf1ae");
         iconMap.Add(Icon.Chrome, "\uf268");
         iconMap.Add(Icon.Circle, "\uf111");
         iconMap.Add(Icon.CircleO, "\uf10c");
         iconMap.Add(Icon.CircleONotch, "\uf1ce");
         iconMap.Add(Icon.CircleThin, "\uf1db");
         iconMap.Add(Icon.Clipboard, "\uf0ea");
         iconMap.Add(Icon.ClockO, "\uf017");
         iconMap.Add(Icon.Clone, "\uf24d");
         iconMap.Add(Icon.Close, "\uf00d");
         iconMap.Add(Icon.Cloud, "\uf0c2");
         iconMap.Add(Icon.CloudDownload, "\uf0ed");
         iconMap.Add(Icon.CloudUpload, "\uf0ee");
         iconMap.Add(Icon.Cny, "\uf157");
         iconMap.Add(Icon.Code, "\uf121");
         iconMap.Add(Icon.CodeFork, "\uf126");
         iconMap.Add(Icon.Codepen, "\uf1cb");
         iconMap.Add(Icon.Codiepie, "\uf284");
         iconMap.Add(Icon.Coffee, "\uf0f4");
         iconMap.Add(Icon.Cog, "\uf013");
         iconMap.Add(Icon.Cogs, "\uf085");
         iconMap.Add(Icon.Columns, "\uf0db");
         iconMap.Add(Icon.Comment, "\uf075");
         iconMap.Add(Icon.CommentO, "\uf0e5");
         iconMap.Add(Icon.Commenting, "\uf27a");
         iconMap.Add(Icon.CommentingO, "\uf27b");
         iconMap.Add(Icon.Comments, "\uf086");
         iconMap.Add(Icon.CommentsO, "\uf0e6");
         iconMap.Add(Icon.Compass, "\uf14e");
         iconMap.Add(Icon.Compress, "\uf066");
         iconMap.Add(Icon.Connectdevelop, "\uf20e");
         iconMap.Add(Icon.Contao, "\uf26d");
         iconMap.Add(Icon.Copy, "\uf0c5");
         iconMap.Add(Icon.Copyright, "\uf1f9");
         iconMap.Add(Icon.CreativeCommons, "\uf25e");
         iconMap.Add(Icon.CreditCard, "\uf09d");
         iconMap.Add(Icon.CreditCardAlt, "\uf283");
         iconMap.Add(Icon.Crop, "\uf125");
         iconMap.Add(Icon.Crosshairs, "\uf05b");
         iconMap.Add(Icon.Css3, "\uf13c");
         iconMap.Add(Icon.Cube, "\uf1b2");
         iconMap.Add(Icon.Cubes, "\uf1b3");
         iconMap.Add(Icon.Cut, "\uf0c4");
         iconMap.Add(Icon.Cutlery, "\uf0f5");
         iconMap.Add(Icon.Dashboard, "\uf0e4");
         iconMap.Add(Icon.Dashcube, "\uf210");
         iconMap.Add(Icon.Database, "\uf1c0");
         iconMap.Add(Icon.Deaf, "\uf2a4");
         iconMap.Add(Icon.Deafness, "\uf2a4");
         iconMap.Add(Icon.Dedent, "\uf03b");
         iconMap.Add(Icon.Delicious, "\uf1a5");
         iconMap.Add(Icon.Desktop, "\uf108");
         iconMap.Add(Icon.Deviantart, "\uf1bd");
         iconMap.Add(Icon.Diamond, "\uf219");
         iconMap.Add(Icon.Digg, "\uf1a6");
         iconMap.Add(Icon.Dollar, "\uf155");
         iconMap.Add(Icon.DotCircleO, "\uf192");
         iconMap.Add(Icon.Download, "\uf019");
         iconMap.Add(Icon.Dribbble, "\uf17d");
         iconMap.Add(Icon.DriversLicense, "\uf2c2");
         iconMap.Add(Icon.DriversLicenseO, "\uf2c3");
         iconMap.Add(Icon.Dropbox, "\uf16b");
         iconMap.Add(Icon.Drupal, "\uf1a9");
         iconMap.Add(Icon.Edge, "\uf282");
         iconMap.Add(Icon.Edit, "\uf044");
         iconMap.Add(Icon.Eercast, "\uf2da");
         iconMap.Add(Icon.Eject, "\uf052");
         iconMap.Add(Icon.EllipsisH, "\uf141");
         iconMap.Add(Icon.EllipsisV, "\uf142");
         iconMap.Add(Icon.Empire, "\uf1d1");
         iconMap.Add(Icon.Envelope, "\uf0e0");
         iconMap.Add(Icon.EnvelopeO, "\uf003");
         iconMap.Add(Icon.EnvelopeOpen, "\uf2b6");
         iconMap.Add(Icon.EnvelopeOpenO, "\uf2b7");
         iconMap.Add(Icon.EnvelopeSquare, "\uf199");
         iconMap.Add(Icon.Envira, "\uf299");
         iconMap.Add(Icon.Eraser, "\uf12d");
         iconMap.Add(Icon.Etsy, "\uf2d7");
         iconMap.Add(Icon.Eur, "\uf153");
         iconMap.Add(Icon.Euro, "\uf153");
         iconMap.Add(Icon.Exchange, "\uf0ec");
         iconMap.Add(Icon.Exclamation, "\uf12a");
         iconMap.Add(Icon.ExclamationCircle, "\uf06a");
         iconMap.Add(Icon.ExclamationTriangle, "\uf071");
         iconMap.Add(Icon.Expand, "\uf065");
         iconMap.Add(Icon.Expeditedssl, "\uf23e");
         iconMap.Add(Icon.ExternalLink, "\uf08e");
         iconMap.Add(Icon.ExternalLinkSquare, "\uf14c");
         iconMap.Add(Icon.Eye, "\uf06e");
         iconMap.Add(Icon.EyeSlash, "\uf070");
         iconMap.Add(Icon.Eyedropper, "\uf1fb");
         iconMap.Add(Icon.Fa, "\uf2b4");
         iconMap.Add(Icon.Facebook, "\uf09a");
         iconMap.Add(Icon.FacebookF, "\uf09a");
         iconMap.Add(Icon.FacebookOfficial, "\uf230");
         iconMap.Add(Icon.FacebookSquare, "\uf082");
         iconMap.Add(Icon.FastBackward, "\uf049");
         iconMap.Add(Icon.FastForward, "\uf050");
         iconMap.Add(Icon.Fax, "\uf1ac");
         iconMap.Add(Icon.Feed, "\uf09e");
         iconMap.Add(Icon.Female, "\uf182");
         iconMap.Add(Icon.FighterJet, "\uf0fb");
         iconMap.Add(Icon.File, "\uf15b");
         iconMap.Add(Icon.FileArchiveO, "\uf1c6");
         iconMap.Add(Icon.FileAudioO, "\uf1c7");
         iconMap.Add(Icon.FileCodeO, "\uf1c9");
         iconMap.Add(Icon.FileExcelO, "\uf1c3");
         iconMap.Add(Icon.FileImageO, "\uf1c5");
         iconMap.Add(Icon.FileMovieO, "\uf1c8");
         iconMap.Add(Icon.FileO, "\uf016");
         iconMap.Add(Icon.FilePdfO, "\uf1c1");
         iconMap.Add(Icon.FilePhotoO, "\uf1c5");
         iconMap.Add(Icon.FilePictureO, "\uf1c5");
         iconMap.Add(Icon.FilePowerpointO, "\uf1c4");
         iconMap.Add(Icon.FileSoundO, "\uf1c7");
         iconMap.Add(Icon.FileText, "\uf15c");
         iconMap.Add(Icon.FileTextO, "\uf0f6");
         iconMap.Add(Icon.FileVideoO, "\uf1c8");
         iconMap.Add(Icon.FileWordO, "\uf1c2");
         iconMap.Add(Icon.FileZipO, "\uf1c6");
         iconMap.Add(Icon.FilesO, "\uf0c5");
         iconMap.Add(Icon.Film, "\uf008");
         iconMap.Add(Icon.Filter, "\uf0b0");
         iconMap.Add(Icon.Fire, "\uf06d");
         iconMap.Add(Icon.FireExtinguisher, "\uf134");
         iconMap.Add(Icon.Firefox, "\uf269");
         iconMap.Add(Icon.FirstOrder, "\uf2b0");
         iconMap.Add(Icon.Flag, "\uf024");
         iconMap.Add(Icon.FlagCheckered, "\uf11e");
         iconMap.Add(Icon.FlagO, "\uf11d");
         iconMap.Add(Icon.Flash, "\uf0e7");
         iconMap.Add(Icon.Flask, "\uf0c3");
         iconMap.Add(Icon.Flickr, "\uf16e");
         iconMap.Add(Icon.FloppyO, "\uf0c7");
         iconMap.Add(Icon.Folder, "\uf07b");
         iconMap.Add(Icon.FolderO, "\uf114");
         iconMap.Add(Icon.FolderOpen, "\uf07c");
         iconMap.Add(Icon.FolderOpenO, "\uf115");
         iconMap.Add(Icon.Font, "\uf031");
         iconMap.Add(Icon.FontAwesome, "\uf2b4");
         iconMap.Add(Icon.Fonticons, "\uf280");
         iconMap.Add(Icon.FortAwesome, "\uf286");
         iconMap.Add(Icon.Forumbee, "\uf211");
         iconMap.Add(Icon.Forward, "\uf04e");
         iconMap.Add(Icon.Foursquare, "\uf180");
         iconMap.Add(Icon.FreeCodeCamp, "\uf2c5");
         iconMap.Add(Icon.FrownO, "\uf119");
         iconMap.Add(Icon.FutbolO, "\uf1e3");
         iconMap.Add(Icon.Gamepad, "\uf11b");
         iconMap.Add(Icon.Gavel, "\uf0e3");
         iconMap.Add(Icon.Gbp, "\uf154");
         iconMap.Add(Icon.Ge, "\uf1d1");
         iconMap.Add(Icon.Gear, "\uf013");
         iconMap.Add(Icon.Gears, "\uf085");
         iconMap.Add(Icon.Genderless, "\uf22d");
         iconMap.Add(Icon.GetPocket, "\uf265");
         iconMap.Add(Icon.Gg, "\uf260");
         iconMap.Add(Icon.GgCircle, "\uf261");
         iconMap.Add(Icon.Gift, "\uf06b");
         iconMap.Add(Icon.Git, "\uf1d3");
         iconMap.Add(Icon.GitSquare, "\uf1d2");
         iconMap.Add(Icon.Github, "\uf09b");
         iconMap.Add(Icon.GithubAlt, "\uf113");
         iconMap.Add(Icon.GithubSquare, "\uf092");
         iconMap.Add(Icon.Gitlab, "\uf296");
         iconMap.Add(Icon.Gittip, "\uf184");
         iconMap.Add(Icon.Glass, "\uf000");
         iconMap.Add(Icon.Glide, "\uf2a5");
         iconMap.Add(Icon.GlideG, "\uf2a6");
         iconMap.Add(Icon.Globe, "\uf0ac");
         iconMap.Add(Icon.Google, "\uf1a0");
         iconMap.Add(Icon.GooglePlus, "\uf0d5");
         iconMap.Add(Icon.GooglePlusCircle, "\uf2b3");
         iconMap.Add(Icon.GooglePlusOfficial, "\uf2b3");
         iconMap.Add(Icon.GooglePlusSquare, "\uf0d4");
         iconMap.Add(Icon.GoogleWallet, "\uf1ee");
         iconMap.Add(Icon.GraduationCap, "\uf19d");
         iconMap.Add(Icon.Gratipay, "\uf184");
         iconMap.Add(Icon.Grav, "\uf2d6");
         iconMap.Add(Icon.Group, "\uf0c0");
         iconMap.Add(Icon.HSquare, "\uf0fd");
         iconMap.Add(Icon.HackerNews, "\uf1d4");
         iconMap.Add(Icon.HandGrabO, "\uf255");
         iconMap.Add(Icon.HandLizardO, "\uf258");
         iconMap.Add(Icon.HandODown, "\uf0a7");
         iconMap.Add(Icon.HandOLeft, "\uf0a5");
         iconMap.Add(Icon.HandORight, "\uf0a4");
         iconMap.Add(Icon.HandOUp, "\uf0a6");
         iconMap.Add(Icon.HandPaperO, "\uf256");
         iconMap.Add(Icon.HandPeaceO, "\uf25b");
         iconMap.Add(Icon.HandPointerO, "\uf25a");
         iconMap.Add(Icon.HandRockO, "\uf255");
         iconMap.Add(Icon.HandScissorsO, "\uf257");
         iconMap.Add(Icon.HandSpockO, "\uf259");
         iconMap.Add(Icon.HandStopO, "\uf256");
         iconMap.Add(Icon.HandshakeO, "\uf2b5");
         iconMap.Add(Icon.HardOfHearing, "\uf2a4");
         iconMap.Add(Icon.Hashtag, "\uf292");
         iconMap.Add(Icon.HddO, "\uf0a0");
         iconMap.Add(Icon.Header, "\uf1dc");
         iconMap.Add(Icon.Headphones, "\uf025");
         iconMap.Add(Icon.Heart, "\uf004");
         iconMap.Add(Icon.HeartO, "\uf08a");
         iconMap.Add(Icon.Heartbeat, "\uf21e");
         iconMap.Add(Icon.History, "\uf1da");
         iconMap.Add(Icon.Home, "\uf015");
         iconMap.Add(Icon.HospitalO, "\uf0f8");
         iconMap.Add(Icon.Hotel, "\uf236");
         iconMap.Add(Icon.Hourglass, "\uf254");
         iconMap.Add(Icon.Hourglass1, "\uf251");
         iconMap.Add(Icon.Hourglass2, "\uf252");
         iconMap.Add(Icon.Hourglass3, "\uf253");
         iconMap.Add(Icon.HourglassEnd, "\uf253");
         iconMap.Add(Icon.HourglassHalf, "\uf252");
         iconMap.Add(Icon.HourglassO, "\uf250");
         iconMap.Add(Icon.HourglassStart, "\uf251");
         iconMap.Add(Icon.Houzz, "\uf27c");
         iconMap.Add(Icon.Html5, "\uf13b");
         iconMap.Add(Icon.ICursor, "\uf246");
         iconMap.Add(Icon.IdBadge, "\uf2c1");
         iconMap.Add(Icon.IdCard, "\uf2c2");
         iconMap.Add(Icon.IdCardO, "\uf2c3");
         iconMap.Add(Icon.Ils, "\uf20b");
         iconMap.Add(Icon.Image, "\uf03e");
         iconMap.Add(Icon.Imdb, "\uf2d8");
         iconMap.Add(Icon.Inbox, "\uf01c");
         iconMap.Add(Icon.Indent, "\uf03c");
         iconMap.Add(Icon.Industry, "\uf275");
         iconMap.Add(Icon.Info, "\uf129");
         iconMap.Add(Icon.InfoCircle, "\uf05a");
         iconMap.Add(Icon.Inr, "\uf156");
         iconMap.Add(Icon.Instagram, "\uf16d");
         iconMap.Add(Icon.Institution, "\uf19c");
         iconMap.Add(Icon.InternetExplorer, "\uf26b");
         iconMap.Add(Icon.Intersex, "\uf224");
         iconMap.Add(Icon.Ioxhost, "\uf208");
         iconMap.Add(Icon.Italic, "\uf033");
         iconMap.Add(Icon.Joomla, "\uf1aa");
         iconMap.Add(Icon.Jpy, "\uf157");
         iconMap.Add(Icon.Jsfiddle, "\uf1cc");
         iconMap.Add(Icon.Key, "\uf084");
         iconMap.Add(Icon.KeyboardO, "\uf11c");
         iconMap.Add(Icon.Krw, "\uf159");
         iconMap.Add(Icon.Language, "\uf1ab");
         iconMap.Add(Icon.Laptop, "\uf109");
         iconMap.Add(Icon.Lastfm, "\uf202");
         iconMap.Add(Icon.LastfmSquare, "\uf203");
         iconMap.Add(Icon.Leaf, "\uf06c");
         iconMap.Add(Icon.Leanpub, "\uf212");
         iconMap.Add(Icon.Legal, "\uf0e3");
         iconMap.Add(Icon.LemonO, "\uf094");
         iconMap.Add(Icon.LevelDown, "\uf149");
         iconMap.Add(Icon.LevelUp, "\uf148");
         iconMap.Add(Icon.LifeBouy, "\uf1cd");
         iconMap.Add(Icon.LifeBuoy, "\uf1cd");
         iconMap.Add(Icon.LifeRing, "\uf1cd");
         iconMap.Add(Icon.LifeSaver, "\uf1cd");
         iconMap.Add(Icon.LightbulbO, "\uf0eb");
         iconMap.Add(Icon.LineChart, "\uf201");
         iconMap.Add(Icon.Link, "\uf0c1");
         iconMap.Add(Icon.Linkedin, "\uf0e1");
         iconMap.Add(Icon.LinkedinSquare, "\uf08c");
         iconMap.Add(Icon.Linode, "\uf2b8");
         iconMap.Add(Icon.Linux, "\uf17c");
         iconMap.Add(Icon.List, "\uf03a");
         iconMap.Add(Icon.ListAlt, "\uf022");
         iconMap.Add(Icon.ListOl, "\uf0cb");
         iconMap.Add(Icon.ListUl, "\uf0ca");
         iconMap.Add(Icon.LocationArrow, "\uf124");
         iconMap.Add(Icon.Lock, "\uf023");
         iconMap.Add(Icon.LongArrowDown, "\uf175");
         iconMap.Add(Icon.LongArrowLeft, "\uf177");
         iconMap.Add(Icon.LongArrowRight, "\uf178");
         iconMap.Add(Icon.LongArrowUp, "\uf176");
         iconMap.Add(Icon.LowVision, "\uf2a8");
         iconMap.Add(Icon.Magic, "\uf0d0");
         iconMap.Add(Icon.Magnet, "\uf076");
         iconMap.Add(Icon.MailForward, "\uf064");
         iconMap.Add(Icon.MailReply, "\uf112");
         iconMap.Add(Icon.MailReplyAll, "\uf122");
         iconMap.Add(Icon.Male, "\uf183");
         iconMap.Add(Icon.Map, "\uf279");
         iconMap.Add(Icon.MapMarker, "\uf041");
         iconMap.Add(Icon.MapO, "\uf278");
         iconMap.Add(Icon.MapPin, "\uf276");
         iconMap.Add(Icon.MapSigns, "\uf277");
         iconMap.Add(Icon.Mars, "\uf222");
         iconMap.Add(Icon.MarsDouble, "\uf227");
         iconMap.Add(Icon.MarsStroke, "\uf229");
         iconMap.Add(Icon.MarsStrokeH, "\uf22b");
         iconMap.Add(Icon.MarsStrokeV, "\uf22a");
         iconMap.Add(Icon.Maxcdn, "\uf136");
         iconMap.Add(Icon.Meanpath, "\uf20c");
         iconMap.Add(Icon.Medium, "\uf23a");
         iconMap.Add(Icon.Medkit, "\uf0fa");
         iconMap.Add(Icon.Meetup, "\uf2e0");
         iconMap.Add(Icon.MehO, "\uf11a");
         iconMap.Add(Icon.Mercury, "\uf223");
         iconMap.Add(Icon.Microchip, "\uf2db");
         iconMap.Add(Icon.Microphone, "\uf130");
         iconMap.Add(Icon.MicrophoneSlash, "\uf131");
         iconMap.Add(Icon.Minus, "\uf068");
         iconMap.Add(Icon.MinusCircle, "\uf056");
         iconMap.Add(Icon.MinusSquare, "\uf146");
         iconMap.Add(Icon.MinusSquareO, "\uf147");
         iconMap.Add(Icon.Mixcloud, "\uf289");
         iconMap.Add(Icon.Mobile, "\uf10b");
         iconMap.Add(Icon.MobilePhone, "\uf10b");
         iconMap.Add(Icon.Modx, "\uf285");
         iconMap.Add(Icon.Money, "\uf0d6");
         iconMap.Add(Icon.MoonO, "\uf186");
         iconMap.Add(Icon.MortarBoard, "\uf19d");
         iconMap.Add(Icon.Motorcycle, "\uf21c");
         iconMap.Add(Icon.MousePointer, "\uf245");
         iconMap.Add(Icon.Music, "\uf001");
         iconMap.Add(Icon.Navicon, "\uf0c9");
         iconMap.Add(Icon.Neuter, "\uf22c");
         iconMap.Add(Icon.NewspaperO, "\uf1ea");
         iconMap.Add(Icon.ObjectGroup, "\uf247");
         iconMap.Add(Icon.ObjectUngroup, "\uf248");
         iconMap.Add(Icon.Odnoklassniki, "\uf263");
         iconMap.Add(Icon.OdnoklassnikiSquare, "\uf264");
         iconMap.Add(Icon.Opencart, "\uf23d");
         iconMap.Add(Icon.Openid, "\uf19b");
         iconMap.Add(Icon.Opera, "\uf26a");
         iconMap.Add(Icon.OptinMonster, "\uf23c");
         iconMap.Add(Icon.Outdent, "\uf03b");
         iconMap.Add(Icon.Pagelines, "\uf18c");
         iconMap.Add(Icon.PaintBrush, "\uf1fc");
         iconMap.Add(Icon.PaperPlane, "\uf1d8");
         iconMap.Add(Icon.PaperPlaneO, "\uf1d9");
         iconMap.Add(Icon.Paperclip, "\uf0c6");
         iconMap.Add(Icon.Paragraph, "\uf1dd");
         iconMap.Add(Icon.Paste, "\uf0ea");
         iconMap.Add(Icon.Pause, "\uf04c");
         iconMap.Add(Icon.PauseCircle, "\uf28b");
         iconMap.Add(Icon.PauseCircleO, "\uf28c");
         iconMap.Add(Icon.Paw, "\uf1b0");
         iconMap.Add(Icon.Paypal, "\uf1ed");
         iconMap.Add(Icon.Pencil, "\uf040");
         iconMap.Add(Icon.PencilSquare, "\uf14b");
         iconMap.Add(Icon.PencilSquareO, "\uf044");
         iconMap.Add(Icon.Percent, "\uf295");
         iconMap.Add(Icon.Phone, "\uf095");
         iconMap.Add(Icon.PhoneSquare, "\uf098");
         iconMap.Add(Icon.Photo, "\uf03e");
         iconMap.Add(Icon.PictureO, "\uf03e");
         iconMap.Add(Icon.PieChart, "\uf200");
         iconMap.Add(Icon.PiedPiper, "\uf2ae");
         iconMap.Add(Icon.PiedPiperAlt, "\uf1a8");
         iconMap.Add(Icon.PiedPiperPp, "\uf1a7");
         iconMap.Add(Icon.Pinterest, "\uf0d2");
         iconMap.Add(Icon.PinterestP, "\uf231");
         iconMap.Add(Icon.PinterestSquare, "\uf0d3");
         iconMap.Add(Icon.Plane, "\uf072");
         iconMap.Add(Icon.Play, "\uf04b");
         iconMap.Add(Icon.PlayCircle, "\uf144");
         iconMap.Add(Icon.PlayCircleO, "\uf01d");
         iconMap.Add(Icon.Plug, "\uf1e6");
         iconMap.Add(Icon.Plus, "\uf067");
         iconMap.Add(Icon.PlusCircle, "\uf055");
         iconMap.Add(Icon.PlusSquare, "\uf0fe");
         iconMap.Add(Icon.PlusSquareO, "\uf196");
         iconMap.Add(Icon.Podcast, "\uf2ce");
         iconMap.Add(Icon.PowerOff, "\uf011");
         iconMap.Add(Icon.Print, "\uf02f");
         iconMap.Add(Icon.ProductHunt, "\uf288");
         iconMap.Add(Icon.PuzzlePiece, "\uf12e");
         iconMap.Add(Icon.Qq, "\uf1d6");
         iconMap.Add(Icon.Qrcode, "\uf029");
         iconMap.Add(Icon.Question, "\uf128");
         iconMap.Add(Icon.QuestionCircle, "\uf059");
         iconMap.Add(Icon.QuestionCircleO, "\uf29c");
         iconMap.Add(Icon.Quora, "\uf2c4");
         iconMap.Add(Icon.QuoteLeft, "\uf10d");
         iconMap.Add(Icon.QuoteRight, "\uf10e");
         iconMap.Add(Icon.Ra, "\uf1d0");
         iconMap.Add(Icon.Random, "\uf074");
         iconMap.Add(Icon.Ravelry, "\uf2d9");
         iconMap.Add(Icon.Rebel, "\uf1d0");
         iconMap.Add(Icon.Recycle, "\uf1b8");
         iconMap.Add(Icon.Reddit, "\uf1a1");
         iconMap.Add(Icon.RedditAlien, "\uf281");
         iconMap.Add(Icon.RedditSquare, "\uf1a2");
         iconMap.Add(Icon.Refresh, "\uf021");
         iconMap.Add(Icon.Registered, "\uf25d");
         iconMap.Add(Icon.Remove, "\uf00d");
         iconMap.Add(Icon.Renren, "\uf18b");
         iconMap.Add(Icon.Reorder, "\uf0c9");
         iconMap.Add(Icon.Repeat, "\uf01e");
         iconMap.Add(Icon.Reply, "\uf112");
         iconMap.Add(Icon.ReplyAll, "\uf122");
         iconMap.Add(Icon.Resistance, "\uf1d0");
         iconMap.Add(Icon.Retweet, "\uf079");
         iconMap.Add(Icon.Rmb, "\uf157");
         iconMap.Add(Icon.Road, "\uf018");
         iconMap.Add(Icon.Rocket, "\uf135");
         iconMap.Add(Icon.RotateLeft, "\uf0e2");
         iconMap.Add(Icon.RotateRight, "\uf01e");
         iconMap.Add(Icon.Rouble, "\uf158");
         iconMap.Add(Icon.Rss, "\uf09e");
         iconMap.Add(Icon.RssSquare, "\uf143");
         iconMap.Add(Icon.Rub, "\uf158");
         iconMap.Add(Icon.Ruble, "\uf158");
         iconMap.Add(Icon.Rupee, "\uf156");
         iconMap.Add(Icon.S15, "\uf2cd");
         iconMap.Add(Icon.Safari, "\uf267");
         iconMap.Add(Icon.Save, "\uf0c7");
         iconMap.Add(Icon.Scissors, "\uf0c4");
         iconMap.Add(Icon.Scribd, "\uf28a");
         iconMap.Add(Icon.Search, "\uf002");
         iconMap.Add(Icon.SearchMinus, "\uf010");
         iconMap.Add(Icon.SearchPlus, "\uf00e");
         iconMap.Add(Icon.Sellsy, "\uf213");
         iconMap.Add(Icon.Send, "\uf1d8");
         iconMap.Add(Icon.SendO, "\uf1d9");
         iconMap.Add(Icon.Server, "\uf233");
         iconMap.Add(Icon.Share, "\uf064");
         iconMap.Add(Icon.ShareAlt, "\uf1e0");
         iconMap.Add(Icon.ShareAltSquare, "\uf1e1");
         iconMap.Add(Icon.ShareSquare, "\uf14d");
         iconMap.Add(Icon.ShareSquareO, "\uf045");
         iconMap.Add(Icon.Shekel, "\uf20b");
         iconMap.Add(Icon.Sheqel, "\uf20b");
         iconMap.Add(Icon.Shield, "\uf132");
         iconMap.Add(Icon.Ship, "\uf21a");
         iconMap.Add(Icon.Shirtsinbulk, "\uf214");
         iconMap.Add(Icon.ShoppingBag, "\uf290");
         iconMap.Add(Icon.ShoppingBasket, "\uf291");
         iconMap.Add(Icon.ShoppingCart, "\uf07a");
         iconMap.Add(Icon.Shower, "\uf2cc");
         iconMap.Add(Icon.SignIn, "\uf090");
         iconMap.Add(Icon.SignLanguage, "\uf2a7");
         iconMap.Add(Icon.SignOut, "\uf08b");
         iconMap.Add(Icon.Signal, "\uf012");
         iconMap.Add(Icon.Signing, "\uf2a7");
         iconMap.Add(Icon.Simplybuilt, "\uf215");
         iconMap.Add(Icon.Sitemap, "\uf0e8");
         iconMap.Add(Icon.Skyatlas, "\uf216");
         iconMap.Add(Icon.Skype, "\uf17e");
         iconMap.Add(Icon.Slack, "\uf198");
         iconMap.Add(Icon.Sliders, "\uf1de");
         iconMap.Add(Icon.Slideshare, "\uf1e7");
         iconMap.Add(Icon.SmileO, "\uf118");
         iconMap.Add(Icon.Snapchat, "\uf2ab");
         iconMap.Add(Icon.SnapchatGhost, "\uf2ac");
         iconMap.Add(Icon.SnapchatSquare, "\uf2ad");
         iconMap.Add(Icon.SnowflakeO, "\uf2dc");
         iconMap.Add(Icon.SoccerBallO, "\uf1e3");
         iconMap.Add(Icon.Sort, "\uf0dc");
         iconMap.Add(Icon.SortAlphaAsc, "\uf15d");
         iconMap.Add(Icon.SortAlphaDesc, "\uf15e");
         iconMap.Add(Icon.SortAmountAsc, "\uf160");
         iconMap.Add(Icon.SortAmountDesc, "\uf161");
         iconMap.Add(Icon.SortAsc, "\uf0de");
         iconMap.Add(Icon.SortDesc, "\uf0dd");
         iconMap.Add(Icon.SortDown, "\uf0dd");
         iconMap.Add(Icon.SortNumericAsc, "\uf162");
         iconMap.Add(Icon.SortNumericDesc, "\uf163");
         iconMap.Add(Icon.SortUp, "\uf0de");
         iconMap.Add(Icon.Soundcloud, "\uf1be");
         iconMap.Add(Icon.SpaceShuttle, "\uf197");
         iconMap.Add(Icon.Spinner, "\uf110");
         iconMap.Add(Icon.Spoon, "\uf1b1");
         iconMap.Add(Icon.Spotify, "\uf1bc");
         iconMap.Add(Icon.Square, "\uf0c8");
         iconMap.Add(Icon.SquareO, "\uf096");
         iconMap.Add(Icon.StackExchange, "\uf18d");
         iconMap.Add(Icon.StackOverflow, "\uf16c");
         iconMap.Add(Icon.Star, "\uf005");
         iconMap.Add(Icon.StarHalf, "\uf089");
         iconMap.Add(Icon.StarHalfEmpty, "\uf123");
         iconMap.Add(Icon.StarHalfFull, "\uf123");
         iconMap.Add(Icon.StarHalfO, "\uf123");
         iconMap.Add(Icon.StarO, "\uf006");
         iconMap.Add(Icon.Steam, "\uf1b6");
         iconMap.Add(Icon.SteamSquare, "\uf1b7");
         iconMap.Add(Icon.StepBackward, "\uf048");
         iconMap.Add(Icon.StepForward, "\uf051");
         iconMap.Add(Icon.Stethoscope, "\uf0f1");
         iconMap.Add(Icon.StickyNote, "\uf249");
         iconMap.Add(Icon.StickyNoteO, "\uf24a");
         iconMap.Add(Icon.Stop, "\uf04d");
         iconMap.Add(Icon.StopCircle, "\uf28d");
         iconMap.Add(Icon.StopCircleO, "\uf28e");
         iconMap.Add(Icon.StreetView, "\uf21d");
         iconMap.Add(Icon.Strikethrough, "\uf0cc");
         iconMap.Add(Icon.Stumbleupon, "\uf1a4");
         iconMap.Add(Icon.StumbleuponCircle, "\uf1a3");
         iconMap.Add(Icon.Subscript, "\uf12c");
         iconMap.Add(Icon.Subway, "\uf239");
         iconMap.Add(Icon.Suitcase, "\uf0f2");
         iconMap.Add(Icon.SunO, "\uf185");
         iconMap.Add(Icon.Superpowers, "\uf2dd");
         iconMap.Add(Icon.Superscript, "\uf12b");
         iconMap.Add(Icon.Support, "\uf1cd");
         iconMap.Add(Icon.Table, "\uf0ce");
         iconMap.Add(Icon.Tablet, "\uf10a");
         iconMap.Add(Icon.Tachometer, "\uf0e4");
         iconMap.Add(Icon.Tag, "\uf02b");
         iconMap.Add(Icon.Tags, "\uf02c");
         iconMap.Add(Icon.Tasks, "\uf0ae");
         iconMap.Add(Icon.Taxi, "\uf1ba");
         iconMap.Add(Icon.Telegram, "\uf2c6");
         iconMap.Add(Icon.Television, "\uf26c");
         iconMap.Add(Icon.TencentWeibo, "\uf1d5");
         iconMap.Add(Icon.Terminal, "\uf120");
         iconMap.Add(Icon.TextHeight, "\uf034");
         iconMap.Add(Icon.TextWidth, "\uf035");
         iconMap.Add(Icon.Th, "\uf00a");
         iconMap.Add(Icon.ThLarge, "\uf009");
         iconMap.Add(Icon.ThList, "\uf00b");
         iconMap.Add(Icon.Themeisle, "\uf2b2");
         iconMap.Add(Icon.Thermometer, "\uf2c7");
         iconMap.Add(Icon.Thermometer0, "\uf2cb");
         iconMap.Add(Icon.Thermometer1, "\uf2ca");
         iconMap.Add(Icon.Thermometer2, "\uf2c9");
         iconMap.Add(Icon.Thermometer3, "\uf2c8");
         iconMap.Add(Icon.Thermometer4, "\uf2c7");
         iconMap.Add(Icon.ThermometerEmpty, "\uf2cb");
         iconMap.Add(Icon.ThermometerFull, "\uf2c7");
         iconMap.Add(Icon.ThermometerHalf, "\uf2c9");
         iconMap.Add(Icon.ThermometerQuarter, "\uf2ca");
         iconMap.Add(Icon.ThermometerThreeQuarters, "\uf2c8");
         iconMap.Add(Icon.ThumbTack, "\uf08d");
         iconMap.Add(Icon.ThumbsDown, "\uf165");
         iconMap.Add(Icon.ThumbsODown, "\uf088");
         iconMap.Add(Icon.ThumbsOUp, "\uf087");
         iconMap.Add(Icon.ThumbsUp, "\uf164");
         iconMap.Add(Icon.Ticket, "\uf145");
         iconMap.Add(Icon.Times, "\uf00d");
         iconMap.Add(Icon.TimesCircle, "\uf057");
         iconMap.Add(Icon.TimesCircleO, "\uf05c");
         iconMap.Add(Icon.TimesRectangle, "\uf2d3");
         iconMap.Add(Icon.TimesRectangleO, "\uf2d4");
         iconMap.Add(Icon.Tint, "\uf043");
         iconMap.Add(Icon.ToggleDown, "\uf150");
         iconMap.Add(Icon.ToggleLeft, "\uf191");
         iconMap.Add(Icon.ToggleOff, "\uf204");
         iconMap.Add(Icon.ToggleOn, "\uf205");
         iconMap.Add(Icon.ToggleRight, "\uf152");
         iconMap.Add(Icon.ToggleUp, "\uf151");
         iconMap.Add(Icon.Trademark, "\uf25c");
         iconMap.Add(Icon.Train, "\uf238");
         iconMap.Add(Icon.Transgender, "\uf224");
         iconMap.Add(Icon.TransgenderAlt, "\uf225");
         iconMap.Add(Icon.Trash, "\uf1f8");
         iconMap.Add(Icon.TrashO, "\uf014");
         iconMap.Add(Icon.Tree, "\uf1bb");
         iconMap.Add(Icon.Trello, "\uf181");
         iconMap.Add(Icon.Tripadvisor, "\uf262");
         iconMap.Add(Icon.Trophy, "\uf091");
         iconMap.Add(Icon.Truck, "\uf0d1");
         iconMap.Add(Icon.Try, "\uf195");
         iconMap.Add(Icon.Tty, "\uf1e4");
         iconMap.Add(Icon.Tumblr, "\uf173");
         iconMap.Add(Icon.TumblrSquare, "\uf174");
         iconMap.Add(Icon.TurkishLira, "\uf195");
         iconMap.Add(Icon.Tv, "\uf26c");
         iconMap.Add(Icon.Twitch, "\uf1e8");
         iconMap.Add(Icon.Twitter, "\uf099");
         iconMap.Add(Icon.TwitterSquare, "\uf081");
         iconMap.Add(Icon.Umbrella, "\uf0e9");
         iconMap.Add(Icon.Underline, "\uf0cd");
         iconMap.Add(Icon.Undo, "\uf0e2");
         iconMap.Add(Icon.UniversalAccess, "\uf29a");
         iconMap.Add(Icon.University, "\uf19c");
         iconMap.Add(Icon.Unlink, "\uf127");
         iconMap.Add(Icon.Unlock, "\uf09c");
         iconMap.Add(Icon.UnlockAlt, "\uf13e");
         iconMap.Add(Icon.Unsorted, "\uf0dc");
         iconMap.Add(Icon.Upload, "\uf093");
         iconMap.Add(Icon.Usb, "\uf287");
         iconMap.Add(Icon.Usd, "\uf155");
         iconMap.Add(Icon.User, "\uf007");
         iconMap.Add(Icon.UserCircle, "\uf2bd");
         iconMap.Add(Icon.UserCircleO, "\uf2be");
         iconMap.Add(Icon.UserMd, "\uf0f0");
         iconMap.Add(Icon.UserO, "\uf2c0");
         iconMap.Add(Icon.UserPlus, "\uf234");
         iconMap.Add(Icon.UserSecret, "\uf21b");
         iconMap.Add(Icon.UserTimes, "\uf235");
         iconMap.Add(Icon.Users, "\uf0c0");
         iconMap.Add(Icon.Vcard, "\uf2bb");
         iconMap.Add(Icon.VcardO, "\uf2bc");
         iconMap.Add(Icon.Venus, "\uf221");
         iconMap.Add(Icon.VenusDouble, "\uf226");
         iconMap.Add(Icon.VenusMars, "\uf228");
         iconMap.Add(Icon.Viacoin, "\uf237");
         iconMap.Add(Icon.Viadeo, "\uf2a9");
         iconMap.Add(Icon.ViadeoSquare, "\uf2aa");
         iconMap.Add(Icon.VideoCamera, "\uf03d");
         iconMap.Add(Icon.Vimeo, "\uf27d");
         iconMap.Add(Icon.VimeoSquare, "\uf194");
         iconMap.Add(Icon.Vine, "\uf1ca");
         iconMap.Add(Icon.Vk, "\uf189");
         iconMap.Add(Icon.VolumeControlPhone, "\uf2a0");
         iconMap.Add(Icon.VolumeDown, "\uf027");
         iconMap.Add(Icon.VolumeOff, "\uf026");
         iconMap.Add(Icon.VolumeUp, "\uf028");
         iconMap.Add(Icon.Warning, "\uf071");
         iconMap.Add(Icon.Wechat, "\uf1d7");
         iconMap.Add(Icon.Weibo, "\uf18a");
         iconMap.Add(Icon.Weixin, "\uf1d7");
         iconMap.Add(Icon.Whatsapp, "\uf232");
         iconMap.Add(Icon.Wheelchair, "\uf193");
         iconMap.Add(Icon.WheelchairAlt, "\uf29b");
         iconMap.Add(Icon.Wifi, "\uf1eb");
         iconMap.Add(Icon.WikipediaW, "\uf266");
         iconMap.Add(Icon.WindowClose, "\uf2d3");
         iconMap.Add(Icon.WindowCloseO, "\uf2d4");
         iconMap.Add(Icon.WindowMaximize, "\uf2d0");
         iconMap.Add(Icon.WindowMinimize, "\uf2d1");
         iconMap.Add(Icon.WindowRestore, "\uf2d2");
         iconMap.Add(Icon.Windows, "\uf17a");
         iconMap.Add(Icon.Won, "\uf159");
         iconMap.Add(Icon.Wordpress, "\uf19a");
         iconMap.Add(Icon.Wpbeginner, "\uf297");
         iconMap.Add(Icon.Wpexplorer, "\uf2de");
         iconMap.Add(Icon.Wpforms, "\uf298");
         iconMap.Add(Icon.Wrench, "\uf0ad");
         iconMap.Add(Icon.Xing, "\uf168");
         iconMap.Add(Icon.XingSquare, "\uf169");
         iconMap.Add(Icon.YCombinator, "\uf23b");
         iconMap.Add(Icon.YCombinatorSquare, "\uf1d4");
         iconMap.Add(Icon.Yahoo, "\uf19e");
         iconMap.Add(Icon.Yc, "\uf23b");
         iconMap.Add(Icon.YcSquare, "\uf1d4");
         iconMap.Add(Icon.Yelp, "\uf1e9");
         iconMap.Add(Icon.Yen, "\uf157");
         iconMap.Add(Icon.Yoast, "\uf2b1");
         iconMap.Add(Icon.Youtube, "\uf167");
         iconMap.Add(Icon.YoutubePlay, "\uf16a");
         iconMap.Add(Icon.YoutubeSquare, "\uf166");
      }
   }
}