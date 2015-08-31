package com.maialovic.personalaudioplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrea on 24/08/14.
 */

public class Song implements Serializable{

    private String mPath;
    private String mTitle;
    private String mAuthor;
    private String mAlbum;
    private String mGender;
    private boolean hasImage;

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public void setmAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public Song(String iPath, Context iCtx)
    {
        this.mPath = iPath;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        if (this.mPath != null);
        {
            mmr.setDataSource(this.mPath);
            String b = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            if (b != null && !b.isEmpty())
                this.mAuthor = b;
            else
                this.mAuthor = "Unknown";

            b = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            if (b != null && !b.isEmpty())
                this.mAlbum = b;
            else
                this.mAlbum = "Unknown";

            b = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            if (b != null && !b.isEmpty())
                this.mTitle = b;
            else
                this.mTitle = "Unknown";

            b = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
            if (b != null && !b.isEmpty())
                this.mGender = b;
            else
                this.mGender = "Other";

            byte[] data = null;

            try
            {
                data = mmr.getEmbeddedPicture();
            }
            catch (Exception ex)
            {

            };

            hasImage = (data != null);

            editGenre();

            mmr.release();
        }
    }

    public void editGenre() {

        Map<String, String> mp3typos = new HashMap<String, String>();
        mp3typos.put("0", "Blues");
        mp3typos.put("1", "ClasicRock");
        mp3typos.put("2", "Country");
        mp3typos.put("3", "Dance");
        mp3typos.put("4", "Disco");
        mp3typos.put("5", "Funk");
        mp3typos.put("6", "Grunge");
        mp3typos.put("7", "Hip-Hop");
        mp3typos.put("8", "Jazz");
        mp3typos.put("9", "Metal");
        mp3typos.put("10", "NewAge");
        mp3typos.put("11", "Oldies");
        mp3typos.put("12", "Other");
        mp3typos.put("13", "Pop");
        mp3typos.put("14", "R&B");
        mp3typos.put("15", "Rap");
        mp3typos.put("16", "Reggae");
        mp3typos.put("17", "Rock");
        mp3typos.put("18", "Techno");
        mp3typos.put("19", "Industrial");
        mp3typos.put("20", "Alternative");
        mp3typos.put("21", "Ska");
        mp3typos.put("22", "DeathMetal");
        mp3typos.put("23", "Pranks");
        mp3typos.put("24", "Soundtrack");
        mp3typos.put("25", "Euro-Techno");
        mp3typos.put("26", "Ambient");
        mp3typos.put("27", "Trip-Hop");
        mp3typos.put("28", "Vocal");
        mp3typos.put("29", "Jazz+Funk");
        mp3typos.put("30", "Fusion");
        mp3typos.put("31", "Trance");
        mp3typos.put("32", "Classical");
        mp3typos.put("33", "Instrumental");
        mp3typos.put("34", "Acid");
        mp3typos.put("35", "House");
        mp3typos.put("36", "Game");
        mp3typos.put("37", "SoundClip");
        mp3typos.put("38", "Gospel");
        mp3typos.put("39", "Noise");
        mp3typos.put("40", "AlternativeRock");
        mp3typos.put("41", "Bass");
        mp3typos.put("42", "Soul");
        mp3typos.put("43", "Punk");
        mp3typos.put("44", "Space");
        mp3typos.put("45", "Meditative");
        mp3typos.put("46", "InstrumentalPop");
        mp3typos.put("47", "InstrumentalRock");
        mp3typos.put("48", "Ethnic");
        mp3typos.put("49", "Gothic");
        mp3typos.put("50", "Darkwave");
        mp3typos.put("51", "Techno-Industrial");
        mp3typos.put("52", "Electronic");
        mp3typos.put("53", "Pop-Folk");
        mp3typos.put("54", "Eurodance");
        mp3typos.put("55", "Dream");
        mp3typos.put("56", "SouthernRock");
        mp3typos.put("57", "Comedy");
        mp3typos.put("58", "Cult");
        mp3typos.put("59", "Gangsta");
        mp3typos.put("60", "Top");
        mp3typos.put("61", "ChristianRap");
        mp3typos.put("62", "Pop/Funk");
        mp3typos.put("63", "Jungle");
        mp3typos.put("64", "NativeUS");
        mp3typos.put("65", "Cabaret");
        mp3typos.put("66", "New");
        mp3typos.put("67", "Psychadelic");
        mp3typos.put("68", "Rave");
        mp3typos.put("69", "Showtunes");
        mp3typos.put("70", "Trailer");
        mp3typos.put("71", "Lo-Fi");
        mp3typos.put("72", "Tribal");
        mp3typos.put("73", "AcidPunk");
        mp3typos.put("74", "AcidJazz");
        mp3typos.put("75", "Polka");
        mp3typos.put("76", "Retro");
        mp3typos.put("77", "Musical");
        mp3typos.put("78", "Rock&Roll");
        mp3typos.put("79", "Hard");
        mp3typos.put("80", "Folk");
        mp3typos.put("81", "Folk-Rock");
        mp3typos.put("82", "NationalFolk");
        mp3typos.put("83", "Swing");
        mp3typos.put("84", "FastFusion");
        mp3typos.put("85", "Bebob");
        mp3typos.put("86", "Latin");
        mp3typos.put("87", "Revival");
        mp3typos.put("88", "Celtic");
        mp3typos.put("89", "Bluegrass");
        mp3typos.put("90", "Avantgarde");
        mp3typos.put("91", "GothicRock");
        mp3typos.put("92", "ProgressiveRock");
        mp3typos.put("93", "PsychedelicRock");
        mp3typos.put("94", "SymphonicRock");
        mp3typos.put("95", "SlowRock");
        mp3typos.put("96", "BigBand");
        mp3typos.put("97", "Chorus");
        mp3typos.put("98", "EasyListening");
        mp3typos.put("99", "Acoustic");
        mp3typos.put("100", "Humour");
        mp3typos.put("101", "Speech");
        mp3typos.put("102", "Chanson");
        mp3typos.put("103", "Opera");
        mp3typos.put("104", "ChamberMusic");
        mp3typos.put("105", "Sonata");
        mp3typos.put("106", "Symphony");
        mp3typos.put("107", "BootyBass");
        mp3typos.put("108", "Primus");
        mp3typos.put("109", "PornGroove");
        mp3typos.put("110", "Satire");
        mp3typos.put("111", "SlowJam");
        mp3typos.put("112", "Club");
        mp3typos.put("113", "Tango");
        mp3typos.put("114", "Samba");
        mp3typos.put("115", "Folklore");
        mp3typos.put("116", "Ballad");
        mp3typos.put("117", "PowerBallad");
        mp3typos.put("118", "RhytmicSoul");
        mp3typos.put("119", "Freestyle");
        mp3typos.put("120", "Duet");
        mp3typos.put("121", "PunkRock");
        mp3typos.put("122", "DrumSolo");
        mp3typos.put("123", "Acapella");
        mp3typos.put("124", "Euro-House");
        mp3typos.put("125", "DanceHall");
        mp3typos.put("126", "Goa");
        mp3typos.put("127", "Drum&Bass");
        mp3typos.put("128", "Club-House");
        mp3typos.put("129", "Hardcore");
        mp3typos.put("130", "Terror");
        mp3typos.put("131", "Indie");
        mp3typos.put("132", "BritPop");
        mp3typos.put("133", "Negerpunk");
        mp3typos.put("134", "PolskPunk");
        mp3typos.put("135", "Beat");
        mp3typos.put("136", "ChristianGangsta");
        mp3typos.put("137", "HeavyMetal");
        mp3typos.put("138", "BlackMetal");
        mp3typos.put("139", "Crossover");
        mp3typos.put("140", "ContemporaryC");
        mp3typos.put("141", "ChristianRock");
        mp3typos.put("142", "Merengue");
        mp3typos.put("143", "Salsa");
        mp3typos.put("144", "ThrashMetal");
        mp3typos.put("145", "Anime");
        mp3typos.put("146", "JPop");
        mp3typos.put("147", "SynthPop");

        String g = getmGender();

        if (g == null || g.isEmpty() || g.equals(null)) {
            String a = mp3typos.get("12").toString();
            this.setmGender(a);
            g = "Other";
            return;
        }

       if (g.length() > 2 && g.length() < 6) {
            if (g.charAt(0) == "(".charAt(0)) {
                g = g.substring(1, g.length() - 1);
                String a = mp3typos.get(g).toString();
                this.setmGender(a);
            }
           return;
        }

        if (isInteger(g) && g.length() > 0 && g.length() < 4) {

            int posa = 0;
            while (posa < g.length() || posa != Integer.parseInt(g))
                posa++;

            if (posa < mp3typos.size()) {
                String a = mp3typos.get(g).toString();
                this.setmGender(a);
            }
        }
    }

        public static boolean isInteger(String s) {
            try {
                Integer.parseInt(s);
            } catch(NumberFormatException e) {
                return false;
            }
            return true;
        }

    public Bitmap getmCover() {

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if (this.mPath != null)
        mmr.setDataSource(this.mPath);

        if(hasImage) {
            try {
                byte[] data = mmr.getEmbeddedPicture();
                return BitmapFactory.decodeByteArray(data, 0, data.length);
            } catch (Exception ex)
            {
                return BitmapFactory.decodeResource(Player.activity.getResources(), R.drawable.no_cover);
            }
        }
        else
        {
            return BitmapFactory.decodeResource(Player.activity.getResources(), R.drawable.no_cover);
        }
    }
}
